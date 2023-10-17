package BasicTests;


import Utils.SerializedNormal;
import com.jml.gorigrama.GorigramaEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SerializedNormalTest {
    
    /*
    Of course all the procedure of validating the save/Open procress must be atomized and intentionally
    disaggregated on purpose for isolation and etc...etc..
    
    */

    List<String> archivosPreexistentes;
    String userProjectPath = System.getProperty("user.dir") + "\\src\\test\\java\\";

    @Before
    public void setUp() {
        archivosPreexistentes = Stream.of(new File(userProjectPath).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }

    @After
    public void tearDown() {
        List<String> postTest = Stream.of(new File(userProjectPath).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
        for (String string : postTest) {
            if (!archivosPreexistentes.contains(string)) {
                File file = new File(userProjectPath + string);
                System.out.println("Se eliminó el archivo: " + string);
                file.delete();
            }
        }
    }

    @Test
    public void saveSerializedNormalNoError() throws IOException {
        String wrongFileName = "crucigrama.slzd";
        int CeldasH = 15;
        int CeldasV = 23;
        String expectedFileName = "crucigrama.slzd";
        File file = new File(userProjectPath);
        SerializedNormal.saveSerializedNormal(new GorigramaEntity(CeldasV, CeldasV), new File(userProjectPath + wrongFileName));
        Assertions.assertDoesNotThrow(() -> SerializedNormal.loadSerialized(new File(userProjectPath + expectedFileName)));
    }

    @Test
    @Ignore
    public void saveSerializedNormalBadName() throws IOException {
        String wrongFileName = "crucigrama";
        int CeldasH = 15;
        int CeldasV = 23;
        String expectedFileName = "crucigrama.slzd";
        File file = new File(userProjectPath);
        SerializedNormal.saveSerializedNormal(new GorigramaEntity(CeldasV, CeldasV), new File(userProjectPath + wrongFileName));
        Assertions.assertThrows(FileNotFoundException.class, () -> SerializedNormal.loadSerialized(new File(userProjectPath + expectedFileName)));
    }

    @Test
    public void saveSerializedNormalFixName() throws IOException {
        String wrongFileName = "crucigrama";
        int CeldasH = 15;
        int CeldasV = 23;
        String expectedFileName = "crucigrama.slzd";
        File file = new File(userProjectPath);
        SerializedNormal.saveSerializedNormal(new GorigramaEntity(CeldasV, CeldasH), new File(userProjectPath + wrongFileName));
        Assertions.assertDoesNotThrow(() -> SerializedNormal.loadSerialized(new File(userProjectPath + expectedFileName)));
    }
  
     //@CsvSource({"0123456789", "0076543210", "0234560987"})
    
    /*
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
    
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.6.0</version>
 
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-params</artifactId>
            <version>5.0.0-RC3</version>        
    */
    @ParameterizedTest
    @CsvSource({"sd.slz,sd.slzd","sd.SLZD,sd.slzd"})
    public void saveSerializedNormalFixOffsetExtension_CSV(String bad, String correct) throws IOException {
      
        int CeldasH = 15;
        int CeldasV = 23;
 
        SerializedNormal.saveSerializedNormal(new GorigramaEntity(CeldasV, CeldasH), new File(userProjectPath + bad));
        Assertions.assertDoesNotThrow(() -> SerializedNormal.loadSerialized(new File(userProjectPath + correct)));
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/SourceCSvs/fileNames.csv", delimiter = ';')
    public void saveSerializedNormalFixOffsetExtension_FileSource(String bad, String correct) throws IOException {
      
        int CeldasH = 15;
        int CeldasV = 23;
 
        SerializedNormal.saveSerializedNormal(new GorigramaEntity(CeldasV, CeldasH), new File(userProjectPath + bad));
        Assertions.assertDoesNotThrow(() -> SerializedNormal.loadSerialized(new File(userProjectPath + correct)));
    }
   

}
