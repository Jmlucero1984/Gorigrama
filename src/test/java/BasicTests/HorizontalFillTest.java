package BasicTests;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


/* @author jmlucero */
public class HorizontalFillTest {

    //TEST 1: Only verifies empty cells number
    @ParameterizedTest
    @CsvFileSource(resources = "/SourceCSvs/horizontalRows.csv", delimiter = ';')
    public void findEmptyCellsTest(String rowStr, int cantSpaces) {
        // String[] row ="cabeza tornillo # campilongo".replaceAll("#", " ".repeat(cantSpaces)).split("");
        String[] row = rowStr.replaceAll("#", " ".repeat(cantSpaces)).split("");
        //System.out.println(row.length);
        int init = 0;
        int end = 0;
        int largo = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i].equals(" ")) {
                init = i;
                end = init;
                while (i < row.length && row[i].equals(" ")) {
                    i++;
                }
                end = i;
                if (end - init > 1) {
                    largo = end - init;
                    if (init != 0) {
                        largo--;
                    }
                    if (end < row.length - 1) {
                        largo--;
                    }
                }
            }
        }
        assertEquals(cantSpaces, largo);
    }

    //TEST 2: Only verifies initIndex and endIndex
    @ParameterizedTest
    @CsvFileSource(resources = "/SourceCSvs/horizontalRowsIndexes.csv", delimiter = ';')
    public void findEmptySubSpaceIndexes(String rowStr, int cantSpaces, int startExpected, int endExpected) {
        String[] row = rowStr.replaceAll("#", " ".repeat(cantSpaces)).split("");
        int init = 0;
        int end = 0;
        int[] nums = {0, 0, 0};
        System.out.println("ROW LENGTH: " + row.length);
        for (int i = 0; i < row.length; i++) {
            if (row[i].equals(" ")) {
                init = i;
                end = init;
                while (i < row.length && row[i].equals(" ")) {
                    i++;
                }
                end = i;
                if (end - init > 1) {
                    if (init != 0) {
                        init++;
                    }
                    if (end < row.length - 1) {
                        end--;
                    }
                    nums[0] = end - init;
                    nums[1] = init;
                    nums[2] = --end;
                    System.out.println(String.format("value1: %s,value2: %s, value3: %s", nums[0], nums[1], nums[2]));
                    break;
                }
            }
        }
        assertAll(() -> {
            assertEquals(cantSpaces, nums[0]);
            assertEquals(startExpected, nums[1]);
            assertEquals(endExpected, nums[2]);
        });
    }

    //TEST 3: Just to fill and aproximatelly three words empty space
    @RepeatedTest(5)
    public void basicHorizontalFillTest() {
        int tentativeWords = 3;
        Random rdm = new Random();
        int tentativeEmptyCells = tentativeWords * 5 + rdm.nextInt(3);
        String rowStr = "Loro capricornio # templo casamiento";
        System.out.println("TENTATIVE EMPY CELLS: " + tentativeEmptyCells);
        String[] row = rowStr.replaceAll("#", " ".repeat(tentativeEmptyCells)).split("");
        int[] indexes = getEmptySpaceIndexes(row);
        List<String> source = getWordsSource();
        Collections.shuffle(source);
        String[] selected = new String[3];
        boolean finished = false;
        for (int i = 0; i < source.size() && !finished; i++) {
            selected[0] = source.get(i);
            for (int j = 0; j < source.size() && !finished; j++) {
                if (i != j) {
                    selected[1] = source.get(j);
                    for (int k = 0; k < source.size() && !finished; k++) {
                        if (j != k && i != k) {
                            selected[2] = source.get(k);
                            if (selected[0].length() + selected[1].length() + selected[2].length() + 2 == indexes[0]) {
                                finished = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        //WARNING!!! The previous conditions looks for the best fit, although three loops got finished,
        //there is a "solution" for default that does not fulfills!
        int pointer = indexes[1];
        for (int i = 0; i < selected.length; i++) {
            String[] letters = selected[i].split("");
            for (int j = 0; j < letters.length; j++) {
                row[pointer] = letters[j];
                pointer++;
            }
            pointer++;
        }
        System.out.println("FINAL ROW: " + concat(row));
        Set<String> uniquewords = new TreeSet();
        uniquewords.addAll(Arrays.asList(concat(row).split(" ")));
        assertTrue(uniquewords.size() == 7);
    }

    //TEST 4: First we fills the blanks just to obtain some restrictions. Then we try to
    //fulfill these restrictions
    @RepeatedTest(10)
    public void BHFill_Restricted_Test() {
        // FIRST PART: GENERATING RESTRICTIONS
        int tentativeWords = 3;
        Random rdm = new Random();
        int tentativeEmptyCells = tentativeWords * 5 + rdm.nextInt(3);
        int[] fixedRestrictions = {18, 20, 25, 30};
        String rowStr = "Loro capricornio # templo casamiento";
        System.out.println("TENTATIVE EMPY CELLS: " + tentativeEmptyCells);
        String[] row = rowStr.replaceAll("#", " ".repeat(tentativeEmptyCells)).split("");
        List<String> source = getWordsSource();
        Collections.shuffle(source);
        String[] filledRow = getFilledRow(Arrays.copyOf(row, row.length), source);
        String[] restrictingLetters = " ".repeat(row.length).split("");
        for (int i = 0; i < fixedRestrictions.length; i++) {
            restrictingLetters[fixedRestrictions[i]] = filledRow[fixedRestrictions[i]];
        }
        System.out.println("------");
        System.out.println(concat(row));
        System.out.println(concat(filledRow));
        System.out.println(concat(restrictingLetters));

        //SECOND PART: LOOKING FOR WORDS FULLFILLING RESTRICTIONS
        Collections.shuffle(source);
        String[] selected = new String[3];
        int[] indexes = getEmptySpaceIndexes(row);
        boolean finished = false;
        for (int i = 0; i < source.size() && !finished; i++) {
            if (fullfillsRestrictions(source.get(i), restrictingLetters, indexes[1])) {
                selected[0] = source.get(i);
                for (int j = 0; j < source.size() && !finished; j++) {
                    if (i != j) {
                        if (fullfillsRestrictions(source.get(j), restrictingLetters, indexes[1] + selected[0].length() + 1)) {
                            selected[1] = source.get(j);
                            for (int k = 0; k < source.size() && !finished; k++) {
                                if (j != k && i != k) {
                                    if (fullfillsRestrictions(source.get(k), restrictingLetters, indexes[1] + selected[0].length() + selected[1].length() + 2)) {
                                        selected[2] = source.get(k);
                                        if (selected[0].length() + selected[1].length() + selected[2].length() + 2 == indexes[0]) {
                                            finished = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int pointer = indexes[1];
        for (int i = 0; i < selected.length; i++) {
            String[] letters = selected[i].split("");
            for (int j = 0; j < letters.length; j++) {
                row[pointer] = letters[j];
                pointer++;
            }
            pointer++;
        }
        System.out.println("FINAL ::");
        System.out.println(concat(row));
        Set<String> uniquewords = new TreeSet();
        uniquewords.addAll(Arrays.asList(concat(row).split(" ")));
        assertAll(() -> {
            assertTrue(uniquewords.size() == 7);
            assertTrue(checkRestrictions(row, restrictingLetters));
        });
    }

    //TEST 5: First we fills the blanks just to obtain some restrictions. Then we try to
    //fulfill these restrictions. USING RECURSION
    @RepeatedTest(10)
    public void B_FFill_Restricted_Recursion_Test() {
        // FIRST PART: GENERATING RESTRICTIONS
        System.out.println("------- B_FFill_Restricted_Recursion_Test ------");
        int tentativeWords = 3;
        Random rdm = new Random();
        int tentativeEmptyCells = tentativeWords * 5 + rdm.nextInt(3);
        int[] fixedRestrictions = {18, 20, 25, 30};
        String rowStr = "Loro capricornio # templo casamiento";
        System.out.println("TENTATIVE EMPY CELLS: " + tentativeEmptyCells);
        String[] row = rowStr.replaceAll("#", " ".repeat(tentativeEmptyCells)).split("");
        List<String> source = getWordsSource();
        Collections.shuffle(source);
        String[] filledRow = getFilledRow(Arrays.copyOf(row, row.length), source);
        String[] restrictingLetters = " ".repeat(row.length).split("");
        for (int i = 0; i < fixedRestrictions.length; i++) {
            restrictingLetters[fixedRestrictions[i]] = filledRow[fixedRestrictions[i]];
        }
        System.out.println("------");
        System.out.println(concat(row));
        System.out.println(concat(filledRow));
        System.out.println(concat(restrictingLetters));

        //SECOND PART: LOOKING FOR WORDS FULLFILLING RESTRICTIONS - WITH RECURSION
        Collections.shuffle(source);
        int[] indexes = getEmptySpaceIndexes(row);
        class RecursionFill {
            public boolean fillWords(int emptyLength, int start, List<String> source, List<String> placed, String[] restrictions) {
                for (int i = 0; i < source.size(); i++) {
                    String word = source.get(i);
                    int wL = word.length();
                    if (!placed.contains(word) && fullfillsRestrictions(word, restrictions, start) && wL <= emptyLength) {
                        placed.add(word);
                        if (wL == emptyLength) {
                            return true;
                        } else if (emptyLength - word.length() > 3) {
                            boolean result = fillWords(emptyLength - 1 - wL, start + wL + 1, source, placed, restrictions);
                            if (result) {
                                return result;
                            } else {
                                placed.remove(placed.size() - 1);
                            }
                        } else {
                            placed.remove(placed.size() - 1);
                        }
                    }
                }
                return false;
            }
        }
        RecursionFill rFill = new RecursionFill();
        List<String> selectedWords = new ArrayList();
        rFill.fillWords(indexes[0], indexes[1], source, selectedWords, restrictingLetters);
        int pointer = indexes[1];
        for (int i = 0; i < selectedWords.size(); i++) {
            String[] letters = selectedWords.get(i).split("");
            for (int j = 0; j < letters.length; j++) {
                row[pointer] = letters[j];
                pointer++;
            }
            pointer++;
        }
        System.out.println("FINAL ::");
        System.out.println(concat(row));
        Set<String> uniquewords = new TreeSet();
        uniquewords.addAll(Arrays.asList(concat(row).split(" ")));
        assertAll(() -> {
            assertTrue(uniquewords.size() == 7);
            assertTrue(checkRestrictions(row, restrictingLetters));
        });
    }

    public boolean checkRestrictions(String[] row, String[] restrictions) {
        for (int i = 0; i < restrictions.length && !restrictions[i].equals(" "); i++) {
            if (!row[i].equals(restrictions[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean fullfillsRestrictions(String word, String[] restrictions, int init) {
        for (int i = 0; i < word.length(); i++) {
            if (!restrictions[init + i].equals(" ")) {
                if (!restrictions[init + i].equals(word.substring(i, i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public <T> T[] copyArray(T[] source) {
        T[] out = (T[]) new Object[source.length];
        return out;
    }

    public String[] getFilledRow(String[] rowToFill, List<String> wordsSource) {

        String[] selected = new String[3];
        int[] indexes = getEmptySpaceIndexes(rowToFill);
        boolean finished = false;
        for (int i = 0; i < wordsSource.size() && !finished; i++) {
            selected[0] = wordsSource.get(i);
            for (int j = 0; j < wordsSource.size() && !finished; j++) {
                if (i != j) {
                    selected[1] = wordsSource.get(j);
                    for (int k = 0; k < wordsSource.size() && !finished; k++) {
                        if (j != k && i != k) {
                            selected[2] = wordsSource.get(k);
                            if (selected[0].length() + selected[1].length() + selected[2].length() + 2 == indexes[0]) {
                                finished = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        //WARNING!!! The previous conditions looks for the best fit, although three loops got finished,
        //there is a "solution" for default that does not fulfills!
        int pointer = indexes[1];
        for (int i = 0; i < selected.length; i++) {
            String[] letters = selected[i].split("");
            for (int j = 0; j < letters.length; j++) {

                rowToFill[pointer] = letters[j];
                pointer++;
            }
            pointer++;

        }
        return rowToFill;

    }

    public List<String> getWordsSource() {
        String words = "cara,cera,casa,pico,taco,tenis,sucre,lepra,tetra,tirol,megane,mentir,paleta,pelota,cantera,pantera,pilotes";
        return Arrays.asList(words.split(","));

    }

    public String concat(String[] letters) {
        String out = "";
        for (int i = 0; i < letters.length; i++) {
            out += letters[i];
        }
        return out;
    }

    public int[] getEmptySpaceIndexes(String[] row) {

        int init = 0;
        int end = 0;
        int[] nums = {0, 0, 0};
        for (int i = 0; i < row.length; i++) {
            if (row[i].equals(" ")) {
                init = i;
                end = init;
                while (i < row.length && row[i].equals(" ")) {
                    i++;
                }
                end = i;

                if (end - init > 1) {
                    if (init != 0) {
                        init++;
                    }
                    if (end < row.length - 1) {
                        end--;
                    }
                    nums[0] = end - init;
                    nums[1] = init;
                    nums[2] = --end;

                    //  System.out.println(String.format("value1: %s,value2: %s, value3: %s", nums[0], nums[1], nums[2]));
                    break;
                }

            }
        }
        return nums;

    }

}
