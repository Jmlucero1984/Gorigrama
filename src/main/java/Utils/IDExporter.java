package Utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

 
public class IDExporter {
    

    String outputChain = "<UNICODE-WIN>\r\n"
        + "<vsn:12.1><fset:InDesign-Roman><ctable:=<Black:COLOR:CMYK:Process:0,0,0,1>>\r\n"
        + "<dps:NormalParagraphStyle=<Nextstyle:NormalParagraphStyle>>\r\n";
    int sizeTitle=14;
    int sizeText=10;

    public IDExporter(int sizeTitle, int sizeText) {
        this.sizeText=sizeText;
        this.sizeTitle=sizeTitle;
    }
     

    public void addItem(String n, String sentence) {
         outputChain += "<pstyle:><ct:Bold><cf:Rotis Serif Std>"+n+"<ct:><cf:>.<cs:"+sizeText+".000000><cf:Rotis Serif Std>"+sentence+"<cs:><cf:>";
    }

    public  void addTitle(String titulo) {
        outputChain +="<pstyle:><ct:Bold><cs:"+sizeTitle+".000000><cf:Rotis Serif Std>"+titulo+"<ct:><cs:><cf:>\r\n";
    }

    public void saveToFile() throws IOException {
        String name = SessionData.newSessionEntry();
        System.setProperty("line.separator","\r\n");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("C:\\Users\\jmluc\\Desktop\\GR"+name+".txt", false), "UTF-16LE");
        BufferedWriter fbw = new BufferedWriter(writer);
        fbw.write(outputChain);
        fbw.close();
    }
}
