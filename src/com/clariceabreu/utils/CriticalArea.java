package com.clariceabreu.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CriticalArea {
    private static ArrayList<String> txt;
    private static Scanner sc;
    private static Random random = new Random();

    //Lê o arquivo e passa para o arranjo de strings
    public static void getTxtFromFile() {
        try {
            txt = new ArrayList<>();
            sc = new Scanner(new File("src/txt/bd.txt"));
            while (sc.hasNext()) {
                txt.add(sc.nextLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    public static void readerAccess() {
        for (int i = 0; i < 100; i++) {
            //Lê a palavra de uma linha aleatória do texto
            String string = txt.get(random.nextInt(txt.size()));
        }
    }

    public static void writerAccess() {
        for (int i = 0; i < 100; i++) {
            //Altera a palavra de uma linha aleatória do texto para MODIFICADO
            txt.set(random.nextInt(txt.size()), "MODIFICADO");
        }
    }

}
