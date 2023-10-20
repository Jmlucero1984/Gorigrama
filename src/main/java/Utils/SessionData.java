
package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/* @author jmlucero */
public class SessionData {
    
    static List<String> sessions =new ArrayList();
    static DecimalFormat dc= new DecimalFormat("00000");
    static int session=0;
    static LocalDate date;
    
    public static void saveSession() throws FileNotFoundException, IOException{
        File file;
        date = LocalDate.now();
        LocalDate old;
        BufferedReader br;
        String st;
        String path="src\\main\\resources\\UserPreferences\\userData.txt";
        file=new File(path);
        br= new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
                sessions.add(st);
        }
        if(!sessions.isEmpty()){
            session =Integer.parseInt(sessions.get(sessions.size()-1).split(",")[0]);
            old =LocalDate.parse(sessions.get(sessions.size()-1).split(",")[1]);
            System.out.println("OLD DATA: "+old);
            session++;
        }
        br.close();
        FileWriter fw = new FileWriter(path,true); //the true will append the new data
        fw.write(dc.format(session)+","+date.toString()+"\n");//appends the string to the file
        fw.close();
  
    }

    public static String newSessionEntry() throws IOException {
        saveSession();
        return dc.format(session)+"_"+date.toString();
    }

}
