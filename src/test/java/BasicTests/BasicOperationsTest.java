
package BasicTests;
 
import org.junit.jupiter.api.Test;


/* @author jmlucero */
public class BasicOperationsTest {
    
    
    @Test
    public void tansposeMatrix() {
        String[][] originalMatrix = new String[3][7];
        originalMatrix[0] = new String[]{"a","b","c","d","e","f","g"};
        originalMatrix[1] = new String[]{"f","g","h","i","j","k","l"};
        originalMatrix[2] = new String[]{"m","n","ñ","o","p","q","r"};
        
        String[][] transposedMatrix = new String[7][3];
        transposedMatrix[0] = new String[]{"g","l","r"};
        transposedMatrix[1] = new String[]{"f","k","q"};
        transposedMatrix[2] = new String[]{"e","j","p"};
        transposedMatrix[3] = new String[]{"d","i","o"};
        transposedMatrix[4] = new String[]{"c","h","ñ"};
        transposedMatrix[5] = new String[]{"b","g","n"};
        transposedMatrix[6] = new String[]{"a","f","m"};
        printMatrix(originalMatrix);
        System.out.println("------");
        printMatrix(transposeMatrix(originalMatrix,true));
        System.out.println("------");
         printMatrix(transposeMatrix(transposedMatrix,false));
        
    }
    
    public String[][] transposeMatrix(String[][] matrix,boolean cc) {
        String[][] output = new String[matrix[0].length][matrix.length];
        for (int i = 0; i <matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                
                output[i][j] = cc? matrix[j][matrix[0].length-1-i]:matrix[matrix.length-1-j][i];
            }
        }
        return output;
    }
    
    public void printMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j <matrix[0].length; j++) {
                System.out.print(matrix[i][j]+" ");
            }
               System.out.print("]");
               System.out.println("");
        }
    }

}
