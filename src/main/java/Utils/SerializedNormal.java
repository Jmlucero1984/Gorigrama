package Utils;

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
     
    public static String fixFileName(File file, String extensionWithoutPoint) {
         String name=file.getName().trim();
         int posPoint = name.lastIndexOf(".");
         if(posPoint!=-1) {
            if(similarLetters(name.substring(posPoint+1, name.length()),extensionWithoutPoint)>=extensionWithoutPoint.length()-1){
                name =name.substring(0, posPoint+1);
            } else if (posPoint==name.length()-1) {
            } else {
                name+=".";
            }
                 
        } else {
             name+=".";
        }
        return  name+extensionWithoutPoint;
    }
    
    private  static int similarLetters(String comparing, String mustBe) {
        int cantLeft=0;
        int cantRight=0;
        for (int i = 0; i < Math.min(comparing.length(),mustBe.length()); i++) {
            if(comparing.substring(i, i+1).equalsIgnoreCase(mustBe.substring(i, i+1))){
                cantLeft++;
            }
        }
         for (int i = 0; i < Math.min(comparing.length(),mustBe.length()); i++) {
            if(comparing.substring(comparing.length()-1-i, comparing.length()-i).equalsIgnoreCase(mustBe.substring(mustBe.length()-1-i, mustBe.length()-i))){
                cantRight++;
            }
        }
         return Integer.max(cantLeft, cantRight);
   
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
