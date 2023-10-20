
package GUI.Bundles;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JButton;
 

/* @author jmlucero */
public class BundleMng {
    
    static ResourceBundle bundle =  ResourceBundle.getBundle("Bundle");
    
  
    public static void getStrings(List<JButton> componentes) {
        System.out.println("Locale "+Locale.getDefault()); //es_AR
      Map<String,String> localeMap = getMap(Locale.getDefault().toString());
        for (JButton componente : componentes) {
            if(localeMap.containsKey(componente.getText())) {
                componente.setText(localeMap.get(componente.getText()));
            }
        }
            
 
 
    }
    
    private static Map<String,String> getMap(String local) {
           Map<String,String> strMap = new HashMap();
        if(local.equals("es_AR")) {
     
        strMap.put("Generate", "GENERAR");
        strMap.put("Draw Grid", "DIBUJAR GRILLA");
        strMap.put("Place Numbers", "COLOCAR NUMEROS");
        strMap.put("Show Letters", "MOSTRAR LETRAS");
        strMap.put("Set Defs", "CREAR DEFINICIONES");
        strMap.put("Edit Defs", "EDITAR DEFINICIONES");
        strMap.put("Save jpg", "GUARDAR IMAGEN");
        strMap.put("Update", "ACTUALIZAR");
        strMap.put("Save Local", "GUARDAR LOCAL");
        strMap.put("Load Local", "CARGAR LOCAL");
        strMap.put("Load DB", "CARGAR DE DB");
        strMap.put("Save DB", "GUARDAR EN DB");
        strMap.put("Word Tool", "HERRAMIENTAS");
        }
        return strMap;
    }
    
    

}
