
package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/* @author jmlucero */
public class CRUD_Serialized_DB {
    private static final String database_user = "Jose";
    private static final String db = "crucigramas";
    private static final String pass = "";
    private static String url = "jdbc:mysql://192.168.1.6:3306/crucigrama";
    
    /**
    * Hash table based implementation of the {@code Map} interface.  This
    * implementation provides all of the optional map operations, and permits
    * {@code null} values and the {@code null} 
    * <p>An instance of {@code HashMap} has two parameters that affect its
    * performance: <i>initial capacity</i> and <i>load factor</i>. {@link Comparable}, 
    * <strong>Note that this implementation is not synchronized.</strong>
    * <i>must</i> be
    *  * If no such object exists, the map should be "wrapped" using the
    * {@link Collections#synchronizedMap Collections.synchronizedMap}
    * method.  This is best done at creation time, to prevent accidental
    * unsynchronized access to the map:<pre>
    * Map m = Collections.synchronizedMap(new HashMap(...));</pre>
    * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
    * Java Collections Framework</a>.
    * in <em>The Java Tutorial</em>, in the section
    * <br><br>
     * <ul>
     * <li><code>DO_NOTHING_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     *
     *
     * <li><code>EXIT_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Exit the application using the <code>System</code>
     * <code>exit</code> method.  Use this only in applications.
     * </ul>
    *   
    * {@inheritDoc}
    * @param path path La ruta donde se encuentra el crucigrama en format slzd
    * @throws IOException Excepción al tratar de acceder al archivo local 
    * @throws ClassNotFoundException ClassNotFound al no poder localizar el driver jdbc
    * @throws SQLException  SQLException al tratar de conectar con la Base de Datos
    * 
    */
    public static void sendSerializedAsBlob(String path) throws IOException, ClassNotFoundException, SQLException {
 
        Class.forName("com.mysql.cj.jdbc.Driver");
        Date date = new Date();
        String fecha = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateStr = simpleDateFormat.format(new Date());
        System.out.println(dateStr);
        System.out.println("FECHA: " + fecha);
        //String path = "src\\main\\resources\\UserPreferences\\GR-00029_2023-09-24.slzd";
         File file =new File(path);
    
        FileInputStream fileInput = new FileInputStream(file);
        byte[] fileData  = fileInput.readAllBytes();
        String sql = "INSERT INTO crucigramas (date,crucigrama) VALUES (?, ?)";
 
        try (Connection con = DriverManager.getConnection(url, database_user, pass)) {
         // Crear una declaración preparada
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1,new java.sql.Date(date.getTime()));
            preparedStatement.setBytes(2, fileData);
            // Ejecutar la consulta
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("El archivo se ha insertado correctamente en la base de datos.");
            } else {
                System.out.println("No se pudo insertar el archivo en la base de datos.");
            }
        }
    }
    public static void getLastBlobAndSaveAsSerialized(String path) throws IOException, ClassNotFoundException, SQLException {
        Blob blob =null;
        try ( Connection con = DriverManager.getConnection(url, database_user, pass)) {
            System.out.println("Connection Established successfully");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM crucigramas ORDER BY id DESC LIMIT 1"); // Execute query
     
            while (!rs.isLast()) {
                rs.next();
              blob  =rs.getBlob("crucigrama");
            }
            st.close(); // close statement
            con.close(); // close connection
            System.out.println("Connection Closed....");
        }
        //path = "src\\main\\resources\\UserPreferences\\maxim.slzd";
        File file = new File(path);

        InputStream in = blob.getBinaryStream();
        OutputStream out = new FileOutputStream(file);
        byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
        int len;

        while ((len = in.read(buff)) != -1) {
            out.write(buff, 0, len);
        }
    }
}
    
    
    
    /*
     private static byte[] readFileToBytes(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[50 * 1024]; // Tamaño máximo de 50 KB
        int bytesRead = fileInputStream.read(buffer);
        byte[] fileData = new byte[bytesRead];
        System.arraycopy(buffer, 0, fileData, 0, bytesRead);
        fileInputStream.close();
        return fileData;
    }
     
     
    /*
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
}

String strFilte = "x" + "’" + bytesToHex(file) + "’"

pstmt.setString(3, strFile);*/
    
 