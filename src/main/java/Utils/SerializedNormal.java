package Utils;

import static Utils.FixFileName.fixFileName;
import com.jml.gorigrama.GorigramaEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/* @author jmlucero */
public class SerializedNormal {

    static String filePath = "src/main/resources/userPreferences/";

    public static void saveSerializedNormal(GorigramaEntity crucigrama) throws IOException {
        String name = SessionData.newSessionEntry();
        File file = new File(filePath + "GR-" + name + ".slzd");
        saveSerializedNormal(crucigrama,file);
    }
    
    public static void saveSerializedNormal(GorigramaEntity crucigrama, File file) throws IOException {
    
        FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getParent()+"\\"+fixFileName(file, "slzd")));
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(crucigrama);
            objectOutputStream.flush();
        }
    }
     
  

    public static GorigramaEntity loadSerialized(File file) throws IOException {
        GorigramaEntity cr=null;
 
        FileInputStream fileInputStream = new FileInputStream(file);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
     
                cr = (GorigramaEntity) objectInputStream.readObject();
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(SerializedNormal.class.getName()).log(Level.SEVERE, null, ex);
            }
        return cr;
    }

}
