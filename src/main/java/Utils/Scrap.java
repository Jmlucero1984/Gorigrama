package Utils;

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
import java.util.stream.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* @author jmlucero */
public class Scrap {

    public enum DICT {
        RAE,
        GOOGLE
    }

    public static String getWord(String word, DICT dict) throws MalformedURLException, IOException {
            if(dict==DICT.RAE) {
                return scrapRae(word);
            } else if(dict==DICT.GOOGLE) {
                  return scrapGoogle(word);
            }
            return "";
    }
    private static String scrapGoogle(String word) throws MalformedURLException, IOException {
        String url = "https://www.google.com/search?q=define+"+ word;
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
           // System.out.println(inputLine);
            response.append(inputLine);
        }
        in.close();
        String html = response.toString();
        Document doc = Jsoup.parse(html);
        Elements abbr = doc.getElementsByClass("BNeawe s3v9rd AP7Wnd");
        List<String> defsScrapped = new ArrayList();
        String salida="";
        for (Element def : abbr) {
      
            salida=def.toString().replaceAll("<[^>]+>", "").replaceAll("\n", "").trim();
            if(!defsScrapped.contains(salida)) defsScrapped.add(salida);
        }
      
         return defsScrapped.stream().reduce("", (accumulated, add) ->accumulated+add+"\n");
    }

    private static String scrapRae(String word) throws MalformedURLException, IOException {
        String url = "https://dle.rae.es/" + word;
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
        String salida = "";
        for (Element def : defs) {

            for (Element element : def.getElementsByTag("span")) {
                salida += element.text() + " ";
            }
            System.out.println(salida);
            salida = salida.replaceAll("^\\d[.]\\s", "");
            salida += "\n";

        }

        return salida;
    }

}
