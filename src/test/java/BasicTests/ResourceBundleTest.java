/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BasicTests;
 
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jmlucero
 */
public class ResourceBundleTest {
    
 
 

    @Test
    public void hello() {
        Locale locale = new Locale("es", "ES");
        ResourceBundle exampleBundle = ResourceBundle.getBundle("GUI.ExampleResource_es_AR", locale);

        assertEquals(exampleBundle.getString("currency"), "Pesos");
        assertEquals(exampleBundle.getObject("toUsdRate"), new BigDecimal("0.001")); 
        assertArrayEquals(exampleBundle.getStringArray("cities"), new String[]{"Buenos Aires", "Córdoba"});
    
    }
}
