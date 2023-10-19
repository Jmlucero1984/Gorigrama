
package Utils;

import java.io.File;

/* @author jmlucero */
public class FixFileName {
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
}
