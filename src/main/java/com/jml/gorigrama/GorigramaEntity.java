package com.jml.gorigrama;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import  Utils.Pair;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 
 

/* @author jmlucero */
public class GorigramaEntity implements Serializable {

int MIN_LEN = 5;

    HashMap<String, String> definiciones = new HashMap();
    String[][] palabrasArrayH;
    String[][] palabrasArrayV;
    List<String> horizontales;
    List<String> verticales;
    List<String> alreadyTokenWords = new ArrayList<>();
    Integer[][] numeros;

    List<Pair<Integer, String>> horizPairsList = new ArrayList();
    List<Pair<Integer, String>> vertiPairList = new ArrayList();

    Stack<Pair<Integer, Integer>> posStack = new Stack();

    private int anchoCeldas = 1;
    private int altoCeldas = 1;

    public GorigramaEntity(String[][] palabrasArrayH, String[][] palabrasArrayV, List<String> horizontales, List<String> verticales, Integer[][] numeros) {
        this.palabrasArrayH = palabrasArrayH;
        this.palabrasArrayV = palabrasArrayV;
        this.horizontales = horizontales;
        this.verticales = verticales;
        this.numeros = numeros;
    }

    public GorigramaEntity(int altoCeldas, int anchoCeldas) {
        this.anchoCeldas = anchoCeldas;
        this.altoCeldas = altoCeldas;
        numeros = new Integer[altoCeldas][anchoCeldas];

    }
    
    public GorigramaEntity(){};

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
        for (int i = 0; i < altoCeldas; i++) {
            for (int j = 0; j < anchoCeldas; j++) {
                numeros[i][j] = 0;
            }
        }
        int index = 1;
        int indexOfList = 0;
        for (int i = 0; i < altoCeldas; i++) {
            for (int j = 0; j < anchoCeldas; j++) {
                if (!palabrasArrayH[i][j].equals(" ")) {
                    numeros[i][j] = index;
                    horizPairsList.add(new Pair<>(index, horizontales.get(indexOfList++)));
                    index++;
                }
                while (j < anchoCeldas - 1 && !palabrasArrayH[i][j].equals(" ")) {
                    j++;
                }
            }
        }
        indexOfList = 0;
        for (int j = 0; j < anchoCeldas; j++) {
            for (int i = 0; i < altoCeldas; i++) {
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
                while (i < altoCeldas - 1 && !palabrasArrayV[i][j].equals(" ")) {
                    i++;
                }
            }
        }
        return numeros;
    }

    public void generar() throws FileNotFoundException, IOException {
        horizontales = new ArrayList();
        verticales = new ArrayList();
        palabrasArrayH = new String[altoCeldas][anchoCeldas];
        palabrasArrayV = new String[altoCeldas][anchoCeldas];
        for (int i = 0; i < altoCeldas; i++) {
            for (int j = 0; j < anchoCeldas; j++) {
                palabrasArrayH[i][j] = " ";
                palabrasArrayV[i][j] = " ";
            }
        }
        for (int i = 0; i < altoCeldas; i++) {
            if (i % 2 == 0) {
                palabrasArrayH[i] = encontrarPalabras(anchoCeldas, RepositorioPalabras.getPalabras(), alreadyTokenWords, true);
            }
        }/*
        for (int i = 0; i < anchoCeldas; i++) {
            if (i%2==0) {
                encontrarVerticales(palabrasArrayH, palabrasArrayV, i, altoCeldas, RepositorioPalabras.getPalabrasImpares(), alreadyTokenWords);
            }
        }*/
        refillVerticalWords(true);
        imprimirPalabras(altoCeldas, anchoCeldas, palabrasArrayH);
        System.out.println("           ");
        imprimirPalabras(altoCeldas, anchoCeldas, palabrasArrayV);
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

    public void findBannedWord(int x, int y, boolean remove) {

        if (y >= altoCeldas || y < 0 || x >= anchoCeldas || x < 0) {
            System.out.println("INTRODUJO UNA COORDENADA NP VALIDA");
            return;
        }
        posStack.add(new Pair<>(x, y));
        System.out.println("REMOVE BEST MATCH: " + x + "   " + y);
        String word = "", DIR = "";
        if (x > 0 && !palabrasArrayH[y][x - 1].equals(" ")) {
            DIR = "H";
        }
        if (x < anchoCeldas - 1 && !palabrasArrayH[y][x + 1].equals(" ")) {
            DIR = "H";
        }
        if (y > 0 && !palabrasArrayV[y - 1][x].equals(" ")) {
            DIR = "V";
        }
        if (y < altoCeldas - 1 && !palabrasArrayH[y + 1][x].equals(" ")) {
            DIR = "V";
        }
        System.out.println("DIRECCION " + DIR);
        if (DIR.equals("H")) {
            boolean extreme = false;
            while (x > 0 && !extreme) {
                x--;
                if (x == 0 || palabrasArrayH[y][x].equals(" ")) {
                    extreme = true;
                }
            }
            extreme = false;
            while (!extreme) {
                word += palabrasArrayH[y][x];
                palabrasArrayH[y][x] = " ";
                x++;
                if (x == anchoCeldas || palabrasArrayH[y][x].equals(" ")) {
                    extreme = true;
                }
            }
        }
        if (DIR.equals("V")) {
            boolean extreme = false;
            while (y > 0 && !extreme) {
                y--;
                if (y == 0 || palabrasArrayV[y][x].equals(" ")) {
                    extreme = true;
                }
            }
            extreme = false;
            while (!extreme) {
                word += palabrasArrayV[y][x];
                palabrasArrayV[y][x] = " ";
                y++;
                if (y == altoCeldas || palabrasArrayV[y][x].equals(" ")) {
                    extreme = true;
                }
            }
        }
        word = word.trim();
        System.out.println("PALABRA ENCONTRADA: " + word);
        System.out.println("ORIGINAL: " + bestMatch(word));
        if (remove&&!word.equals("")) {
            try {
                RepositorioPalabras.excludeNewBanned(bestMatch(word));
            } catch (IOException ex) {
                Logger.getLogger(GorigramaEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        alreadyTokenWords.remove(bestMatch(word));
    }

    private String bestMatch(String word) {
        for (int i = 0; i < alreadyTokenWords.size(); i++) {
            if (alreadyTokenWords.get(i).replaceAll("-", "").equals(word)) {
                return alreadyTokenWords.get(i);
            }
        }
        return word;
    }

    public void actualizarHorizontales() {
        String tira = "";
        for (int i = 0; i < altoCeldas; i++) {
            for (int j = 0; j < anchoCeldas; j++) {
                tira += palabrasArrayH[i][j];
            }
            tira += " ";
        }
        horizontales = new ArrayList<>();
        Arrays.asList(tira.split(" ")).stream().filter(t -> !t.equals("")).forEach(t -> horizontales.add(bestMatch(t)));
    }

    public void actualizarVerticales() {
        String tira = "";
        for (int j = 0; j < anchoCeldas; j++) {
            for (int i = 0; i < altoCeldas; i++) {
                tira += palabrasArrayV[i][j];
            }
            tira += " ";
        }
        verticales = new ArrayList<>();
        Arrays.asList(tira.split(" ")).stream().filter(t -> !t.equals("")).forEach(t -> verticales.add(bestMatch(t)));
    }

    public void refillHorizontalWords() {
        imprimirPalabras(altoCeldas, anchoCeldas, palabrasArrayH);
        Random rdm = new Random();
        for (int i = 0; i < altoCeldas; i += 2) {
            System.out.println("NIVEL I: " + i);
            int init = 0, end = 0;
            for (int j = 0; j < anchoCeldas; j++) {
                init = end;
                if (palabrasArrayH[i][j].equals(" ")) {
                    init = j;
                    while (j < anchoCeldas - 1 && palabrasArrayH[i][j + 1].equals(" ")) {
                        j++;
                    }
                    end = j;
                }
                if (end - init > 1) {
                    if (init > 0) {
                        init++;
                    }
                    int largo = end - init;
                    if (j == anchoCeldas - 1) {
                        largo++;
                    }

                    
                    boolean trim = largo <= 10 || rdm.nextGaussian() <= 0;
                    boolean founded=false;
                    boolean goodFit;
                    while(!founded) {
                        String[] resultado = encontrarPalabras(largo, RepositorioPalabras.getPalabras(), alreadyTokenWords, trim);
                       goodFit=true;
                        for (int k = 0; k < resultado.length; k++) {

                            if(!(palabrasArrayV[i][init + k].equals(resultado[k])||palabrasArrayV[i][init + k].equalsIgnoreCase(" "))){
                                goodFit=false;
                                break;
                            }
                        }
                        if(goodFit) {
                            for (int k = 0; k < resultado.length; k++) {
                                  palabrasArrayH[i][init + k] = resultado[k];
                            }
                            founded=true;
                        }
                     
                    }
                    
                }
            }
        }
        //  imprimirPalabras(altoCeldas, anchoCeldas,palabrasArrayH);
        actualizarHorizontales();

    }

    public void refillVerticalWords(boolean allGrid) {

        //imprimirPalabras(altoCeldas, anchoCeldas, palabrasArrayV);
 
        String newWord;
        boolean founded = false;
        boolean goodFit=true;
        int indexOfPalabras=0;
        boolean continuar = true;
    
        for (int j = 0; j < anchoCeldas&&continuar; j+=2) {
            int init = 0, end = 0;
            for (int i = 0; i < altoCeldas-1&&continuar; i +=2) {
                 
                if (palabrasArrayV[i][j].equals(" ")) {
                    init = i;
                    end= i;
                    while (end < altoCeldas - 1 && palabrasArrayV[end + 1][j].equals(" ")) {
                        end++;
                    }
                    
                }
                int largo = end - init + 1;
         /*
            if (end == altoCeldas - 1) {
                    largo++;
                }*/
                if(largo<3) continue;
                while(!founded&&indexOfPalabras<RepositorioPalabras.getPalabrasImpares().size()) {
                    newWord=RepositorioPalabras.getPalabrasImpares().get(indexOfPalabras);
                    if(newWord.length()<=largo &&!alreadyTokenWords.contains(newWord)){
                        goodFit=true;
                        for(int k=0;k<newWord.length();k+=2){
                            if(!(newWord.substring(k, k+1).equalsIgnoreCase(palabrasArrayH[init+k][j]))){
                                goodFit=false;
                                break;
                            }
                        }
                        if(goodFit) {
                            founded=true;
                            for (int k = 0; k < newWord.length(); k++) {
                                palabrasArrayV[init+k][j]=newWord.substring(k,k+1);
                                alreadyTokenWords.add(newWord);
                                
                                
                            }
                            i+=newWord.length()-1;
                            continuar=allGrid;
                        }
                    }
                    indexOfPalabras++;
                    
                    
                }
                
                //REF OLD CODE 001
                 
              indexOfPalabras=0;
                founded = false;
            }
        }
        //  imprimirPalabras(altoCeldas, anchoCeldas,palabrasArrayV);
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

   

    public void forceVertical() {
        imprimirPalabras(altoCeldas, anchoCeldas, palabrasArrayH);
        Random rdm = new Random();
        String word;
        int pos_last_x = posStack.peek().first;
        int pos_last_y = posStack.pop().second;
        int pos_prev_x = posStack.peek().first;
        int pos_prev_y = posStack.pop().second;
        List<String> horiz;
        int[][] lettersPos;
        if (pos_last_x == pos_prev_x && pos_last_y % 2 == 0 && pos_prev_y % 2 == 0) {
            int max;
            if (pos_prev_y > pos_last_y) {
                max = pos_prev_y;
                pos_prev_y = pos_last_y;
                pos_last_y = max;
            }
            int largo = pos_last_y - pos_prev_y + 1;
            boolean founded = false;
            int rows = (largo + 1) / 2;

            lettersPos = new int[rows][3];

            int iters = 10000;
            Collections.shuffle(RepositorioPalabras.getPalabras());
            while (!founded && iters > 0) {
                horiz = new ArrayList();
                iters--;
                int assigned = 0;
                do {
                    word = RepositorioPalabras.getPalabras().get(rdm.nextInt(RepositorioPalabras.getPalabras().size()));
                } while (word.length() != largo || alreadyTokenWords.contains(word));
                for (int i = 0; i < rows; i++) {

                    int a = pos_last_x;
                    lettersPos[i][1] = a;

                    while (a > 0 && palabrasArrayH[pos_prev_y + i * 2][a - 1].equals(" ")) {
                        a--;
                    }

                    if (a != 0) {
                        a++;
                    }
                    lettersPos[i][0] = a;
                    a = pos_last_x;
                    while (a < anchoCeldas - 1 && palabrasArrayH[pos_prev_y + i * 2][a + 1].equals(" ")) {
                        a++;
                    }
                    if (a == anchoCeldas - 1) {
                        a++;
                    }

                    lettersPos[i][2] = a;
                    int wordLength = lettersPos[i][2] - lettersPos[i][0];
                    int atIndex = lettersPos[i][1] - lettersPos[i][0];
                    for (int j = 0; j < RepositorioPalabras.getPalabras().size(); j++) {
                        String temp = RepositorioPalabras.getPalabras().get(j);
                        if (!horiz.contains(temp) && temp.length() == wordLength && String.valueOf(temp.charAt(atIndex)).equals(String.valueOf(word.charAt(i * 2)))) {
                            horiz.add(temp);
                            assigned++;
                            break;

                        }
                    }

                }
                if (assigned == (largo + 1) / 2) {
                    System.out.println("WORDD VERTICAL: " + word);
                    int point = 0;
                    for (int i = pos_prev_y; i < pos_prev_y + word.length(); i++) {
                        palabrasArrayV[i][pos_last_x] = String.valueOf(word.charAt(point));
                        point++;
                    }
                    for (int i = 0; i < horiz.size(); i++) {
                        point = 0;
                        for (int j = lettersPos[i][0]; j < lettersPos[i][0] + horiz.get(i).length(); j++) {
                            palabrasArrayH[pos_prev_y + i * 2][j] = String.valueOf(horiz.get(i).charAt(point));
                            point++;
                        }

                    }
                    founded = true;
                }

            }
            if (founded) {
                actualizarHorizontales();
                actualizarVerticales();
                checkForDuplicates();
            } else {
                System.out.println("FALLÓ FORZAR HORIZONTAL");
            }

        }
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

    public  void setMIN_LEN(int MIN_LEN) {
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

    public  int getMIN_LEN() {
        return MIN_LEN;
    }

    public List<String> getAlreadyTokenWords() {
        return alreadyTokenWords;
    }

    public int getAnchoCeldas() {
        return anchoCeldas;
    }

    public int getAltoCeldas() {
        return altoCeldas;
    }

    public void setVerticales(List<String> verticales) {
        this.verticales = verticales;
    }

    public void setAlreadyTokenWords(List<String> alreadyTokenWords) {
        this.alreadyTokenWords = alreadyTokenWords;
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

    public void setAnchoCeldas(int anchoCeldas) {
        this.anchoCeldas = anchoCeldas;
    }

    public void setAltoCeldas(int altoCeldas) {
        this.altoCeldas = altoCeldas;
    }
    
}
