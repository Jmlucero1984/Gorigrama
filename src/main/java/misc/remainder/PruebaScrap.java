
package misc.remainder;

import Utils.JdbcConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* @author jmlucero */
public class PruebaScrap {
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException, MalformedURLException, IOException {
     
     
        String url = "https://dle.rae.es/pito";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        }
        in.close();
        String html = response.toString();
        Document doc = Jsoup.parse(html);
       
        Element content = doc.getElementById("resultados");
        Elements abbr = content.getElementsByClass("n2");
        Elements defs = content.getElementsByClass("j");
        List<String> defsScrapped = new ArrayList();
        String salida="";
        for (Element def : defs) {
        
            for (Element element : def.getElementsByTag("span")) {
                salida+= element.text()+" ";
            }
            System.out.println(salida);
            salida="";
            
        }
        
        System.out.println(content);
      
    }

}
