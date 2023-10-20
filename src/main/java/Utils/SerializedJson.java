
package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jml.gorigrama.GorigramaEntity;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/* @author jmlucero */
public class SerializedJson {
    
    static String filePath="src/main/resources/userPreferences/";
 
    public static void saveToJson(GorigramaEntity crucigrama) throws IOException {
        String name = SessionData.newSessionEntry();
        File file = new File(filePath+"GR-"+name+".json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, crucigrama);
    }
  
    public static GorigramaEntity loadJson(File file) throws IOException{
        /*
        definiciones        - HashMap<String,String>
        horizontales        - String[][]   
        verticales          - String[][]
        alreadyTakenWords   - List<String>
        numeros             - int[][]
        horizPairsList      - List<Pair<Integer, String>> 
        vertiPairList       - List<Pair<Integer, String>> 
        listHorizontales    - List<String>
        listVerticales      - List<String>
        anchoCeldas         - int
        altoCeldas          - int
        min_LEN             - int
 
        */
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(file);
         rootNode.fieldNames().forEachRemaining(System.out::println);
        GorigramaEntity loadedCrucigrama = new GorigramaEntity();
        loadedCrucigrama.setDefiniciones(objectMapper.readValue(rootNode.get("definiciones").toString(),HashMap.class));
        loadedCrucigrama.setHorizontales(objectMapper.readValue(rootNode.get("horizontales").toString(),List.class));
        loadedCrucigrama.setVerticales(objectMapper.readValue(rootNode.get("verticales").toString(),List.class));
        loadedCrucigrama.setAlreadyTakenWords(objectMapper.readValue(rootNode.get("alreadyTakenWords").toString(),List.class));
        return loadedCrucigrama;
    }
  
  
     public static <T> T[][] createTwoDimensionalArray(Class<T> clase,String cadena) {
         
        List<List<T>> d2lista;
        return null;
    }

}
