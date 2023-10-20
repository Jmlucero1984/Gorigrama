package com.jml.gorigrama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* @author jmlucero */
public class RepositorioPalabras {

    static List<String> palabras;
    static List<String> banned;
    static List<String> postAdded;
    static List<String> palabrasImpares;

    public static List<String> getPalabras() {
        return palabras;
    }

    public static List<String> getPalabrasImpares() {
        return palabrasImpares;
    }

    public static void excludeNewBanned(String word) throws FileNotFoundException, IOException {
        palabras.remove(word);
        File file;
        BufferedReader br;
        String st;
        String path = "src\\main\\resources\\Palabras\\Banned.txt";
        file = new File(path);
        br = new BufferedReader(new FileReader(file));
        FileWriter fw = new FileWriter(path, true); //the true will append the new data
        fw.write(word + "\n");//appends the string to the file
        fw.close();
    }

    public static void addNewWord(String word) throws FileNotFoundException, IOException {
        palabras.add(word);
        File file;
        BufferedReader br;
        String st;
        String path = "src\\main\\resources\\Palabras\\PostAdded.txt";
        file = new File(path);
        FileWriter fw = new FileWriter(path, true); //the true will append the new data
        fw.write(word + "\n");//appends the string to the file
        fw.close();
    }

    public static void load() throws FileNotFoundException, IOException {
        String tira = "A,B,C,D,E,enie,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String[] letrasIndex = tira.split(",");
        palabras = new ArrayList<>();
        banned = new ArrayList<>();
        palabrasImpares = new ArrayList<>();
        postAdded = new ArrayList<>();

        File file;
        BufferedReader br = null;
        String st;
        for (int i = 0; i < letrasIndex.length; i++) {
            String path = "src\\main\\resources\\Palabras\\" + letrasIndex[i] + ".txt";
            file = new File(path);
            br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
                palabras.add(st.toLowerCase());

            }
        }
        String path = "src\\main\\resources\\Palabras\\Banned.txt";
        file = new File(path);
        br = new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
        // Print the string
            banned.add(st);
        }
        path = "src\\main\\resources\\Palabras\\PostAdded.txt";
        file = new File(path);
        br = new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
        // Print the string
            postAdded.add(st.toLowerCase());
        }
        br.close();
        System.out.println("CANTIDAD DE PALABRAS CARGADAS: " + palabras.size());
        System.out.println("CANTIDAD DE PALABRAS BANNED: " + banned.size());
        palabras.removeAll(banned);
        System.out.println("CANTIDAD DE PALABRAS FILTRADAS: " + palabras.size());
        System.out.println("CANTIDAD DE PALABRAS POST ADDED: " + postAdded.size());
        palabras.addAll(postAdded);
        Collections.shuffle(palabras);
        System.out.println("CANTIDAD DE PALABRAS FINAL: " + palabras.size());
        palabrasImpares = palabras.stream().filter(t -> t.length() % 2 == 1 && !t.contains("-")).collect(Collectors.toList());
    }

}
