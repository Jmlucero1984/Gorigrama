package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* @author jmlucero */
public class WordsFileProcessor {

    public static void proccessFiles(String letter) throws FileNotFoundException, IOException {
        String tira = "A";

        List<String> palabras_RAE = new ArrayList<>();
        Set<String> palabrasBritannica = new TreeSet<>((String o1, String o2) -> o1.compareTo(o2));
        Set<String> diferences = new TreeSet<>((String o1, String o2) -> o1.compareTo(o2));
        File file;

        BufferedReader br = null;
        String st;
        
        String path = "src\\main\\resources\\Palabras\\"+letter.toUpperCase()+".txt";
        file = new File(path);
        br = new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
            palabras_RAE.add(st);

        }
        path = "src\\main\\resources\\Britannica\\brit_"+letter+".txt";
        file = new File(path);
        br = new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
            String[] cuts = st.toLowerCase().trim().split(" ");
            for (String cut : cuts) {
                if (cut.startsWith(letter)) {
                    String replace = cut.replace(",","");
                    palabrasBritannica.add(replace);
                }
            }

        }
        path = "src\\main\\resources\\Britannica\\brit_"+letter+"_cleaned.txt";
        FileWriter fw = new FileWriter(path, true); //the true will append the new data
        for (String string : palabrasBritannica) {
            fw.write(string + "\n");//appends the string to the file
        }
        /*
        Issue: Many files not fully filled. With at least 10 up to 20 words not processed. It seems
        to be independent of the file size, and it always left the last word sliced...
        */
    
        for (String string : palabras_RAE) {
            if(!palabrasBritannica.contains(string)){
                diferences.add(string);
            }
        }
        path = "src\\main\\resources\\Britannica\\brit_"+letter+"_diferences.txt";
     
        fw = new FileWriter(path, true); //the true will append the new data
        for (String string : diferences) {
            fw.write(string + "\n");//appends the string to the file
        }
        fw.close();
    }
}