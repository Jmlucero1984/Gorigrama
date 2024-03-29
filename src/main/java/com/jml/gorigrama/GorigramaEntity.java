package com.jml.gorigrama;

import Utils.Direction;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import Utils.Pair;
import Utils.WordItem;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/* @author jmlucero */
public class GorigramaEntity implements Serializable {

    int MIN_LEN = 5;

    HashMap<String, String> definiciones = new HashMap();
    String[][] palabrasArrayH;
    String[][] palabrasArrayV;
    List<String> horizontales;
    List<String> verticales;
    List<String> alreadyTakenWords = new ArrayList<>();
    Integer[][] numeros;

    List<Pair<Integer, String>> horizPairsList = new ArrayList();
    List<Pair<Integer, String>> vertiPairList = new ArrayList();

    Stack<Pair<Integer, Integer>> posStack = new Stack();

    private int numCeldasH = 1;
    private int numCeldasV = 1;

    private int offsety = 0;
    private int offsetx = 0;
/*
    public GorigramaEntity(String[][] palabrasArrayH, String[][] palabrasArrayV, List<String> horizontales, List<String> verticales, Integer[][] numeros) {
        this.palabrasArrayH = palabrasArrayH;
        this.palabrasArrayV = palabrasArrayV;
        this.horizontales = horizontales;
        this.verticales = verticales;
        this.numeros = numeros;
    }*/
    
    public GorigramaEntity(int numCeldasV, int numCeldasH, int offsety, int offsetx) {
        this.numCeldasH = numCeldasH;
        this.numCeldasV = numCeldasV;
        this.offsetx = offsetx;
        this.offsety = offsety;
        numeros = new Integer[numCeldasV][numCeldasH];

    }

    public GorigramaEntity(int numCeldasV, int numCeldasH) {
        this.numCeldasH = numCeldasH;
        this.numCeldasV = numCeldasV;
        this.offsetx = 0;
        this.offsety = 0;
        numeros = new Integer[numCeldasV][numCeldasH];

    }

    public GorigramaEntity() {
    }

    ;

    public void generarDefiniciones() {
        definiciones = new HashMap();
        for (Pair<Integer, String> pair : horizPairsList) {
            definiciones.put(pair.second, "Definicion1?\nDefinicion2?\nDefinicion3?");
        }
        for (Pair<Integer, String> pair : vertiPairList) {
            definiciones.put(pair.second, "Definicion1?\nDefinicion2?\nDefinicion3?");
        }
    }

    public void cleanStack() {
        posStack.clear();
    }

    public Integer[][] generarNumeros() {
        horizPairsList = new ArrayList();
        vertiPairList = new ArrayList();
        for (int i = 0; i < numCeldasV; i++) {
            for (int j = 0; j < numCeldasH; j++) {
                numeros[i][j] = 0;
            }
        }
        int index = 1;
        int indexOfList = 0;
        for (int i = 0; i < numCeldasV; i++) {
            for (int j = 0; j < numCeldasH; j++) {
                if (!palabrasArrayH[i][j].equals(" ")) {
                    numeros[i][j] = index;
                    horizPairsList.add(new Pair<>(index, horizontales.get(indexOfList++)));
                    index++;
                }
                while (j < numCeldasH - 1 && !palabrasArrayH[i][j].equals(" ")) {
                    j++;
                }
            }
        }
        indexOfList = 0;
        for (int j = 0; j < numCeldasH; j++) {
            for (int i = 0; i < numCeldasV; i++) {
                // Controlar inicios de verticales y horizontales en la misma celda, que por ende comparten numero.
                if (!palabrasArrayV[i][j].equals(" ")) {
                    if (numeros[i][j] == 0) {
                        numeros[i][j] = index;
                        vertiPairList.add(new Pair<>(index, verticales.get(indexOfList++)));
                        index++;
                    } else {
                        vertiPairList.add(new Pair<>(numeros[i][j], verticales.get(indexOfList++)));
                    }
                }
                while (i < numCeldasV - 1 && !palabrasArrayV[i][j].equals(" ")) {
                    i++;
                }
            }
        }
        return numeros;
    }

    public void generar() throws FileNotFoundException, IOException {
        horizontales = new ArrayList();
        verticales = new ArrayList();
        palabrasArrayH = new String[numCeldasV][numCeldasH];
        palabrasArrayV = new String[numCeldasV][numCeldasH];
        for (int i = 0; i < numCeldasV; i++) {
            for (int j = 0; j < numCeldasH; j++) {
                palabrasArrayH[i][j] = " ";
                palabrasArrayV[i][j] = " ";
            }
        }
        for (int i = 0; i < numCeldasV; i++) {
            if (i % 2 == 0) {
                palabrasArrayH[i] = encontrarPalabras(numCeldasH, RepositorioPalabras.getPalabras(), alreadyTakenWords, true);
            }
        }

        refillVerticalWords(true);
        imprimirPalabras(numCeldasV, numCeldasH, palabrasArrayH);
        System.out.println("           ");
        imprimirPalabras(numCeldasV, numCeldasH, palabrasArrayV);
    }

    public void encontrarVerticales(String[][] palabrasArrayH, String[][] palabrasArrayV, int vertical, int alto, List<String> palabrasImpares, List<String> yaEncontradas) {
        boolean completed = false;
        int index = 0;
        int limit = 5;
        for (int i = 0; i < palabrasImpares.size() && !completed; i++) {
            String palabra = palabrasImpares.get(i);
            if (index > (int) alto / 2) {
                limit = 3;
            }
            if (palabra.length() >= limit && (palabra.length() + index) < alto && !yaEncontradas.contains(palabra)) {
                boolean goodFit = true;
                for (int j = 0; j < palabra.length(); j++) {
                    if (j % 2 == 0) {
                        if (!String.valueOf(palabra.charAt(j)).equals(palabrasArrayH[j + index][vertical])) {
                            goodFit = false;
                        }
                    }
                }
                if (goodFit) {
                    for (int j = 0; j < palabra.length(); j++) {
                        palabrasArrayV[j + index][vertical] = String.valueOf(palabra.charAt(j));
                    }
                    verticales.add(palabra);
                    yaEncontradas.add(palabra);
                    index += palabra.length() + 1;
                    System.out.println("PALABRA LENGTH: " + palabra.length());
                    System.out.println("INDEX " + index);
                    i = 0;
                }
            }
            if (index > alto - 2) {
                completed = true;
            }
        }

    }

    public void removeWord(WordItem word) {
        if (word.getDir() == Direction.HORIZONTAL) {
            for (int i = word.getInitX(); i < word.getEndX(); i++) {
                palabrasArrayH[word.getInitY()][i] = " ";
            }
            alreadyTakenWords.remove(word.getWord());
            definiciones.remove(word.getWord());
            replaceHorizontals();
            actualizarHorizontales();

            //Does not return to the words source, it's supposed this is a oddly one
        } else if (word.getDir() == Direction.VERTICAL) {
            for (int i = word.getInitY(); i < word.getEndY(); i++) {
                palabrasArrayV[i][word.getInitX()] = " ";
            }
            alreadyTakenWords.remove(word.getWord());
              definiciones.remove(word.getWord());
            refillVerticalWords(true);
            actualizarVerticales();
            //Does not return to the words source, it's supposed this is a oddly one
        }

    }

    public Pair<WordItem, WordItem> locateWords(int x, int y) {

        if (y >= numCeldasV || y < 0 || x >= numCeldasH || x < 0) {
            System.out.println("INTRODUJO UNA COORDENADA NP VALIDA");
            return null;
        }
        WordItem wh = new WordItem();
        WordItem wv = new WordItem();
        int hx = x;
        int hy = y;
        int inithX;
        int endhX;
        System.out.println("COORD X: " + hx);
        System.out.println("COORD Y: " + hy);
        String horizontalWord = "";

        if (!palabrasArrayH[hy][hx].equals(" ")) {
            while (hx != 0 && !palabrasArrayH[hy][hx - 1].equals(" ")) {
                hx--;
            }
            inithX = hx;
            while (hx != numCeldasH && !palabrasArrayH[hy][hx].equals(" ")) {
                horizontalWord += palabrasArrayH[hy][hx];
                hx++;
            }
            endhX = hx;
            System.out.println("PALABRA HORIZONTAL ENCONTRADA: " + horizontalWord);
            System.out.println("ORIGINAL H: " + bestMatch(horizontalWord));
            wh = new WordItem(bestMatch(horizontalWord), inithX, y, endhX, y, Direction.HORIZONTAL);
        }
        int vx = x;
        int vy = y;
        int initvY;
        int endvY;
        String verticalWord = "";
        System.out.println("");
        if (!palabrasArrayV[vy][vx].equals(" ")) {
            while (vy != 0 && !palabrasArrayV[vy - 1][vx].equals(" ")) {
                vy--;
            }
            initvY = vy;
            while (vy != numCeldasV && !palabrasArrayV[vy][vx].equals(" ")) {
                verticalWord += palabrasArrayV[vy][vx];
                vy++;
            }
            endvY = vy;
            System.out.println("PALABRA VERTICAL ENCONTRADA: " + verticalWord);
            System.out.println("ORIGINAL V: " + bestMatch(verticalWord));
            wv = new WordItem(bestMatch(verticalWord), x, initvY, x, endvY, Direction.VERTICAL);
        }

        Pair<WordItem, WordItem> foundedWords = new Pair(wh, wv);

        return foundedWords;
    }

    private String bestMatch(String word) {
        for (int i = 0; i < alreadyTakenWords.size(); i++) {
            if (alreadyTakenWords.get(i).replaceAll("-", "").equals(word)) {
                return alreadyTakenWords.get(i);
            }
        }
        return word;
    }

    public void actualizarHorizontales() {
        String tira = "";
        for (int i = 0; i < numCeldasV; i++) {
            for (int j = 0; j < numCeldasH; j++) {
                tira += palabrasArrayH[i][j];
            }
            tira += " ";
        }
        horizontales = new ArrayList<>();
        Arrays.asList(tira.split(" ")).stream().filter(t -> !t.equals("")).forEach(t -> horizontales.add(bestMatch(t)));
    }

    public void actualizarVerticales() {
        String tira = "";
        for (int j = 0; j < numCeldasH; j++) {
            for (int i = 0; i < numCeldasV; i++) {
                tira += palabrasArrayV[i][j];
            }
            tira += " ";
        }
        verticales = new ArrayList<>();
        Arrays.asList(tira.split(" ")).stream().filter(t -> !t.equals("")).forEach(t -> verticales.add(bestMatch(t)));
    }

    /**
     * This a second stage replace method after implementing contextual menu for oddly ones. The primitive method was {@link #refillHorizontalWords()}.
     *
     */
    public void replaceHorizontals() {
        List<String> wordSource = RepositorioPalabras.getPalabras();
        Collections.shuffle(wordSource);
        for (int i = 0; i < numCeldasV; i += 2) {
            List<String> candidates = new ArrayList();
            int[] indexes = getEmptySpaceIndexes(palabrasArrayH[i]);
            if (indexes[0] > 1) {
                if (findWordsForFilling(indexes[0], indexes[1], wordSource, alreadyTakenWords, candidates, palabrasArrayV[i])) {
                    System.out.println("CANDIDATES");
                    candidates.forEach(System.out::println);
                    alreadyTakenWords.addAll(candidates);
                    fillWithWords(candidates, palabrasArrayH[i], indexes[1]);
                }
            }
        }
        actualizarHorizontales();

    }

    public void fillWithWords(List<String> candidates, String[] horizontalRow, int start) {
        int pointer = start;
        for (int i = 0; i < candidates.size(); i++) {
            String[] letters = candidates.get(i).split("");
            for (int j = 0; j < letters.length; j++) {

                horizontalRow[pointer] = letters[j];
                pointer++;
            }
            pointer++;

        }

    }

    public boolean findWordsForFilling(int emptyLength, int start, List<String> source, List<String> alreadyTaken, List<String> placed, String[] restrictions) {
        for (int i = 0; i < source.size(); i++) {
            String word = source.get(i);
            int wL = word.length();
            if (!placed.contains(word) && !alreadyTaken.contains(word) && wL <= emptyLength && fullfillsRestrictions(word, restrictions, start)) {
                placed.add(word);
                if (wL == emptyLength) {
                    return true;
                } else if (emptyLength - word.length() > 3) {
                    boolean result = findWordsForFilling(emptyLength - 1 - wL, start + wL + 1, source, alreadyTaken, placed, restrictions);
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
                    System.out.println(String.format("value1: %s, value2: %s, value3: %s", nums[0], nums[1], nums[2]));
                    break;
                }

            }
        }
        return nums;

    }

    public boolean fullfillsRestrictions(String word, String[] restrictions, int init) {
        //Added a check for not letting horizontal words end just before a restriction. In a supposed situation like this,
        //return false expecting for a new word shorter or 1 letter larger.
        if (init + word.length() < restrictions.length && !restrictions[init + word.length()].equals(" ")) {
            return false;

        }
        for (int i = 0; i < word.length(); i++) {
            if (!restrictions[init + i].equals(" ")) {
                if (!restrictions[init + i].equals(word.substring(i, i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void refillVerticalWords(boolean allGrid) {

        //imprimirPalabras(numCeldasV, numCeldasH, palabrasArrayV);
        String newWord;
        boolean founded = false;
        boolean goodFit = true;

        boolean continuar = true;

        for (int j = 0; j < numCeldasH && continuar; j += 2) {
            int init = 0, end = 0;
            for (int i = 0; i < numCeldasV - 1 && continuar; i += 2) {
                if (palabrasArrayV[i][j].equals(" ")) {
                    init = i;
                    end = i;
                    while (end < numCeldasV - 2 && palabrasArrayV[end + 2][j].equals(" ") && !palabrasArrayH[end + 2][j].equals(" ")) {
                        end += 2;
                    }
                    int largo = end - init + 1;
                    if (largo < 3) {
                        continue;
                    }
                    List<String> wordSource = RepositorioPalabras.getPalabrasImpares();
                    for (int k = 0; !founded && k < wordSource.size(); k++) {
                        newWord = RepositorioPalabras.getPalabrasImpares().get(k);

                        if (newWord.length() <= largo && !alreadyTakenWords.contains(newWord)) {
                            goodFit = true;
                            for (int l = 0; l < newWord.length(); l += 2) {
                                if (!(newWord.substring(l, l + 1).equalsIgnoreCase(palabrasArrayH[init + l][j]))) {
                                    goodFit = false;
                                    break;
                                }
                            }
                            if (goodFit) {
                                founded = true;
                                for (int l = 0; l < newWord.length(); l++) {
                                    palabrasArrayV[init + l][j] = newWord.substring(l, l + 1);
                                    alreadyTakenWords.add(newWord);

                                }
                                i += newWord.length() - 1;
                                continuar = allGrid;
                            }
                        }
                    }

                }

                //REF OLD CODE 001
                founded = false;
            }
        }
        //  imprimirPalabras(numCeldasV, numCeldasH,palabrasArrayV);
        actualizarVerticales();

    }

    public void checkForDuplicates() {
        Set<String> stringSet = new TreeSet<>((String o1, String o2) -> o1.compareTo(o2));
        verticales.forEach(stringSet::add);
        horizontales.forEach(stringSet::add);
        List<String> listaTemp = new ArrayList();
        listaTemp.addAll(verticales);
        listaTemp.addAll(horizontales);

        if (stringSet.size() != (verticales.size() + horizontales.size())) {
            System.out.println("/////////  PROBLEMA /////////");
            for (String string : stringSet) {
                listaTemp.remove(string);
            }
            System.out.println("EL ULTIMO QUE QUEDÓ EN LA LISTA: " + listaTemp.get(0));
        }
    }

    private static void imprimirPalabras(int alto, int ancho, String[][] palabras) {
        String linea;
        String concatenada;
        for (int i = 0; i < alto; i++) {
            linea = "";
            concatenada = "";
            for (int k = 0; k < ancho; k++) {
                concatenada += palabras[i][k];
            }
            linea = concatenada;
            System.out.println("[" + linea + "]");
        }
    }

    public String[] encontrarPalabras(int ancho, List<String> palabras, List<String> yaEncontradas, boolean trim) {
        Random rdm = new Random();
        int cant = 1;

        if (trim && ancho >= 9 && ancho < 18) {
            cant = 2;
        } else if (ancho >= 18) {
            cant = (int) ancho / 9;
        }
        int sumaLargo;
        String[] words = new String[cant];
        String[] words_no_guion = new String[cant];
        System.out.println("CANT: " + cant);
        String salida = "";
        int cantidad = palabras.size();
        boolean disponible = false;
        while (!disponible) {
            sumaLargo = 0;
            salida = "";
            for (int i = 0; i < cant; i++) {
                words[i] = palabras.get(rdm.nextInt(cantidad));
                words_no_guion[i] = "" + words[i]; //NO REFERENCING
                if (words[i].split(" ").length > 1) {
                    System.out.println("PALABRA CON ESPACIO: " + words[i]);
                }
                if (words[i].split("-").length > 1) {
                    System.out.println("PALABRA CON GUIÓN: " + words[i]);
                    words_no_guion[i] = words[i].replaceAll("-", "");
                    System.out.println("PALABRA SIN GUIÓN: " + words_no_guion[i]);
                }
                sumaLargo += words_no_guion[i].length();
                salida += words_no_guion[i];
                if (i < cant - 1) {
                    salida += " ";
                }
            }
            if ((sumaLargo + cant - 1) == ancho && !checkAlreadyUsed(yaEncontradas, words)) {
                disponible = true;
                yaEncontradas.addAll(Arrays.asList(words));
                horizontales.addAll(Arrays.asList(words));
            }
        }
        checkForDuplicates();
        return salida.split("");
    }

    public static boolean checkAlreadyUsed(List<String> lista, String[] words) {
        boolean used = false;
        for (int i = 0; i < words.length; i++) {
            if (lista.contains(words[i])) {
                used = true;
            }
            break;
        }
        return used;
    }

    public HashMap<String, String> getDefiniciones() {
        return definiciones;
    }

    public Integer[][] getNumeros() {
        return numeros;
    }

    public List<Pair<Integer, String>> getHorizPairsList() {
        return horizPairsList;
    }

    public List<Pair<Integer, String>> getVertiPairList() {
        return vertiPairList;
    }

    public List<String> getListVerticales() {
        return verticales;
    }

    public List<String> getListHorizontales() {
        return horizontales;
    }

    public String[][] getHorizontales() {
        return palabrasArrayH;
    }

    public String[][] getVerticales() {
        return palabrasArrayV;
    }

    public void setMIN_LEN(int MIN_LEN) {
        this.MIN_LEN = MIN_LEN;
    }

    public void setDefiniciones(HashMap<String, String> definiciones) {
        this.definiciones = definiciones;
    }

    public void setPalabrasArrayH(String[][] palabrasArrayH) {
        this.palabrasArrayH = palabrasArrayH;
    }

    public void setPalabrasArrayV(String[][] palabrasArrayV) {
        this.palabrasArrayV = palabrasArrayV;
    }

    public void setHorizontales(List<String> horizontales) {
        this.horizontales = horizontales;
    }

    public int getMIN_LEN() {
        return MIN_LEN;
    }

    public List<String> getAlreadyTakenWords() {
        return alreadyTakenWords;
    }

    public int getNumCeldasH() {
        return numCeldasH;
    }

    public int getNumCeldasV() {
        return numCeldasV;
    }

    public void setVerticales(List<String> verticales) {
        this.verticales = verticales;
    }

    public void setAlreadyTakenWords(List<String> alreadyTakenWords) {
        this.alreadyTakenWords = alreadyTakenWords;
    }

    public void setNumeros(Integer[][] numeros) {
        this.numeros = numeros;
    }

    public void setHorizPairsList(List<Pair<Integer, String>> horizPairsList) {
        this.horizPairsList = horizPairsList;
    }

    public void setVertiPairList(List<Pair<Integer, String>> vertiPairList) {
        this.vertiPairList = vertiPairList;
    }

    public void setPosStack(Stack<Pair<Integer, Integer>> posStack) {
        this.posStack = posStack;
    }

    public void setNumCeldasH(int numCeldasH) {
        this.numCeldasH = numCeldasH;
    }

    public void setNumCeldasV(int numCeldasV) {
        this.numCeldasV = numCeldasV;
    }

    public int getOffsety() {
        return offsety;
    }

    public int getOffsetx() {
        return offsetx;
    }

}
