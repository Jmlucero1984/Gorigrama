package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* @author jmlucero */
public class JdbcConnection {

    private static final String DATABASE_USER = "Jose";
    private static final String DB_NAME = "crucigrama";
    private static final String DB_PASS = "";

    private static String getTableByLetter(String word) {
        char first = word.toLowerCase().charAt(0);
        if (first == 'ñ') {
            return "letra_enie";
        }
        if (first == 'á' || first == 'ä' || first == 'à') {
            return "letra_a";
        }
        if (first == 'é' || first == 'ë' || first == 'è') {
            return "letra_e";
        }
        if (first == 'í' || first == 'ï' || first == 'ì') {
            return "letra_i";
        }
        if (first == 'ó' || first == 'ö' || first == 'ò') {
            return "letra_o";
        }
        if (first == 'ú' || first == 'ü' || first == 'ù') {
            return "letra_u";
        }
        return "letra_" + first;
    }

    static String url = "jdbc:mysql://192.168.1.6:3306/" + DB_NAME; // table details

    public static void executeSave(String word, String definition) throws SQLException, ClassNotFoundException {
        Date date = new Date();
        String fecha = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String datestr = simpleDateFormat.format(new Date());
        System.out.println(datestr);
        System.out.println("FECHA: " + fecha);
        int cantidad = definition.split("\n").length;
        String query_insert = "insert into " + getTableByLetter(word) + " (palabra,largo,lastTime,definicion,cantidad, ultimaDefinicion)"
                + " values ('" + word + "','" + word.length() + "','" + fecha + "','" + definition + "','" + cantidad + "','" + 1 + "')";
        String query = query_insert;
        try (Connection con = DriverManager.getConnection(url, DATABASE_USER, DB_PASS)) {
            System.out.println("Connection Established successfully");
            Statement st = con.createStatement();
            st.execute(query);
            st.close();
            con.close();
            System.out.println("Connection Closed....");
        }

    }

    public static void update(String word, String definition) throws SQLException, ClassNotFoundException {
        try (Connection con = DriverManager.getConnection(url, DATABASE_USER, DB_PASS)) {
            System.out.println("Connection Established successfully");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(QUERY.SELECT(word));

            if (rs.next()) {
                System.out.println("ACTUALIZACIÓN REALIZADA");
                st.execute(QUERY.SELECT(word));
            } else {
                st.execute(QUERY.INSERT(word, definition));
                System.out.println("INSERCIÓN REALIZADA");
            }
            st.close();
            con.close();
            System.out.println("Connection Closed....");
        }

    }

    static class QUERY {
        static String INSERT(String word, String definition) {
            Date date = new Date();
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String datestr = simpleDateFormat.format(new Date());
            System.out.println(datestr);
            int cantidad = definition.split("\n").length;
            String fecha = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            System.out.println("FECHA: " + fecha);
            return "insert into " + getTableByLetter(word) + " (palabra,largo,lastTime,definicion,cantidad, ultimaDefinicion)"
                    + " values ('" + word + "','" + word.length() + "','" + fecha + "','" + definition + "','" + cantidad + "','" + 1 + "')";
        }

        static String SELECT(String word) {
            return "select * from " + getTableByLetter(word) + " where palabra = '" + word + "'"; // query to be run
        }

        static String UPDATE(String word, String definition) {
            return "update " + getTableByLetter(word) + " set definicion ='" + definition + "' where palabra = '" + word + "'";
        }
    }

    public static void updateAll(HashMap<String, String> definiciones) throws SQLException, ClassNotFoundException {

        try (Connection con = DriverManager.getConnection(url, DATABASE_USER, DB_PASS)) {
            System.out.println("Connection Established successfully");
            Statement st = con.createStatement();
            String word;
            String definition;
            for (Map.Entry<String, String> entry : definiciones.entrySet()) {
                word = entry.getKey();
                definition = entry.getValue();
                ResultSet rs = st.executeQuery(QUERY.SELECT(word));
                if (rs.next()) {
                    System.out.println("ACTUALIZACIÓN REALIZADA");
                    st.execute(QUERY.UPDATE(word, definition));
                } else {
                    st.execute(QUERY.INSERT(word, definition));
                    System.out.println("INSERCIÓN REALIZADA");
                }
            }
            st.close();
            con.close();
            System.out.println("Connection Closed....");
        }
    }

    public static void executeRead(String word) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(url, DATABASE_USER, DB_PASS)) {
            System.out.println("Connection Established successfully");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(QUERY.SELECT(word));
            while (!rs.isLast()) {
                rs.next();
                System.out.println(rs.getString("definicion"));
            }
            st.close();
            con.close();
            System.out.println("Connection Closed....");
        }
    }

    public static void executeMultipleRead(HashMap<String, String> hashMap) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query_select;
        ResultSet rs;
        try (Connection con = DriverManager.getConnection(url, DATABASE_USER, DB_PASS)) {
            Statement st = con.createStatement();
            System.out.println("Connection Established successfully");
            for (Map.Entry<String, String> def : hashMap.entrySet()) {
                query_select = "select * from " + getTableByLetter(def.getKey()) + " where palabra = '" + def.getKey() + "'"; //
                st.execute(query_select);
                rs = st.executeQuery(query_select);
                if (rs.next()) {
                    hashMap.replace(def.getKey(), rs.getString("definicion"));
                } else {
                    hashMap.replace(def.getKey(), "No se encontraron definiciones disponibles en la BD");
                }
            }
            st.close();
            con.close();
            System.out.println("Connection Closed....");
        }
    }

}
