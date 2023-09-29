
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
    static List<List<String>> palabras_medidas; 
    static List<String> palabrasImpares; 
    static List<String> palabras_3_5;  
    static List<String> palabras_3_7; 
    static List<String> palabras_3_9;  
    static List<String> palabras_3_11;  
    static List<String> palabras_3_13;

    public static List<String> getPalabras() {
        return palabras;
    }

    public static List<List<String>> getPalabras_medidas() {
        return palabras_medidas;
    }

    public static List<String> getPalabrasImpares() {
        return palabrasImpares;
    }

    public static List<String> getPalabras_3_5() {
        return palabras_3_5;
    }

    public static List<String> getPalabras_3_7() {
        return palabras_3_7;
    }

    public static List<String> getPalabras_3_9() {
        return palabras_3_9;
    }

    public static List<String> getPalabras_3_11() {
        return palabras_3_11;
    }

    public static List<String> getPalabras_3_13() {
        return palabras_3_13;
    }
    
    public static void excludeNewBanned(String word) throws FileNotFoundException, IOException{
        
        palabras.remove(word);
         File file;
        BufferedReader br;
        String st;
        String path="src\\main\\resources\\Palabras\\Banned.txt";
        file=new File(path);
        br= new BufferedReader(new FileReader(file));
        FileWriter fw = new FileWriter(path,true); //the true will append the new data
        fw.write(word+"\n");//appends the string to the file
        fw.close();
        
        
    }
    public static void addNewWord(String word) throws FileNotFoundException, IOException{
        palabras.add(word);
        File file;
        BufferedReader br;
        String st;
        String path="src\\main\\resources\\Palabras\\PostAdded.txt";
        file=new File(path);
      
        FileWriter fw = new FileWriter(path,true); //the true will append the new data
        fw.write(word+"\n");//appends the string to the file
        fw.close();
   
    }
    
    
    public static void load() throws FileNotFoundException, IOException {
        String tira= "A,B,C,D,E,enie,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String[] letrasIndex = tira.split(",");
          palabras = new ArrayList<>();
        banned = new ArrayList<>();
        palabras_medidas= new ArrayList();
        palabrasImpares = new ArrayList<>();
        palabras_3_5 = new ArrayList<>();
        palabras_3_7 = new ArrayList<>();
        palabras_3_9 = new ArrayList<>();
        palabras_3_11 = new ArrayList<>();
        palabras_3_13 = new ArrayList<>();
        postAdded = new ArrayList<>();
        
        
        File file;
 
        BufferedReader br = null;
        String st;
        for(int i=0; i<letrasIndex.length;i++) {
        
            String path="src\\main\\resources\\Palabras\\"+letrasIndex[i]+".txt";
            file=new File(path);
            br= new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
           
                    palabras.add(st);
                
            }
        }
        String path="src\\main\\resources\\Palabras\\Banned.txt";
            file=new File(path);
            br= new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
             
                    // Print the string
                    banned.add(st);
               
            }
          path="src\\main\\resources\\Palabras\\PostAdded.txt";
            file=new File(path);
            br= new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
             
                    // Print the string
                    postAdded.add(st);
               
            }
        br.close();
        System.out.println("CANTIDAD DE PALABRAS CARGADAS: "+palabras.size());
        System.out.println("CANTIDAD DE PALABRAS BANNED: "+banned.size());
        palabras.removeAll(banned);
        System.out.println("CANTIDAD DE PALABRAS FILTRADAS: "+palabras.size());
        System.out.println("CANTIDAD DE PALABRAS POST ADDED: "+postAdded.size());
        palabras.addAll(postAdded);
        Collections.shuffle(palabras);
        System.out.println("CANTIDAD DE PALABRAS FINAL: "+palabras.size());
        palabras_3_5 = palabras.stream().filter(t->t.length()>=3 && t.length()<=5).collect(Collectors.toList());
        palabras_3_7 = palabras.stream().filter(t->t.length()>=3 && t.length()<=7).collect(Collectors.toList());
        palabras_3_9 = palabras.stream().filter(t->t.length()>=3 && t.length()<=9).collect(Collectors.toList());
        palabras_3_11 = palabras.stream().filter(t->t.length()>=3 && t.length()<=11).collect(Collectors.toList());
         palabras_3_13 = palabras.stream().filter(t->t.length()>=3 && t.length()<=13).collect(Collectors.toList());
         //*Ac atenmos el problema de que van a pasar palabras que son impares si se cuentan los guiones... que despues para la comparacion
         // del largo se remueven (no asi para el detalle de la definicion)
         palabrasImpares =  palabras.stream().filter(t->t.length()%2==1 && !t.contains("-")).collect(Collectors.toList());
        palabras_medidas.add(palabras_3_5);
        palabras_medidas.add(palabras_3_7);
        palabras_medidas.add(palabras_3_9);
        palabras_medidas.add(palabras_3_11);
        palabras_medidas.add(palabras_3_13);
   }
    
}
