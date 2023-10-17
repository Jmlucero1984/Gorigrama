
package GUI;

import Utils.IDExporter;
import static Utils.SessionData.saveSession;
import Utils.WordsFileProcessor;
import java.io.IOException;

/* @author jmlucero */
public class PruebaInDesign {
    
    public static void main(String[] args) throws IOException {
        
        WordsFileProcessor.proccessFiles("d");
        WordsFileProcessor.proccessFiles("e");
        WordsFileProcessor.proccessFiles("f");
        WordsFileProcessor.proccessFiles("g");
        WordsFileProcessor.proccessFiles("h");
        WordsFileProcessor.proccessFiles("i");
        
        
        
        
    }
  /* String[] defs = {"1. LAca adofadfafsdfasdf","2. fsdfsfsdfasdfasdfs dfsdf ","3. LA aodf adfaf"};
        for (int i = 0; i < defs.length; i++) {
            String temp =defs[i];
          defs[i]=temp.replaceFirst("^\\d[.]\\s", "");
        }
        for (String def : defs) {
            System.out.println("DEF : "+def.toString());
        }

    }*/
  
  

}
