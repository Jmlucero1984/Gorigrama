
package GUI;

import java.math.BigDecimal;
import java.util.ListResourceBundle;

/* @author jmlucero */
public class ExampleResource_es_AR extends ListResourceBundle{

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
          {"currency", "Pesos"},
          {"toUsdRate", new BigDecimal("0.001")},
          {"cities", new String[] { "Buenos Aires", "Córdoba" }} 
        };
    }

}
