package com.clariceabreu.main;

import com.clariceabreu.entities.Reader;
import com.clariceabreu.entities.Writer;
import com.clariceabreu.enums.Implementations;
import com.clariceabreu.utils.CriticalArea;
import com.clariceabreu.utils.Lock;
import com.clariceabreu.utils.ReaderWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static Lock lock;
    private static ReaderWriter readerWriter;
    private static CriticalArea criticalArea;
    private static Thread[] threads;

    public static void main(String[] args) {
        lock = new Lock();
        readerWriter = new ReaderWriter();
        criticalArea = new CriticalArea();

        //Loop para fazer as três implementacoes propostas
        for (Implementations implementation : Implementations.values()) {
            System.out.println("Implementação: " + implementation.name());
            runImplementation(implementation);
        }
    }

    //Inicia o acesso das 100 Threads ao texto
    private static void runImplementation(Implementations implementation) {
        long sum = 0;
        System.out.println("Escritores, Leitores, Tempo,");

        //Loop para definir as proporcoes entre leitores e escritores
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j < 50; j++) { //Loop para fazer 50 vezes cada uma das proporções
                createThreads(implementation, i); //Cria um novo arranjo de threads a cada execução
                criticalArea.getTxtFromFile();
                long start = System.currentTimeMillis();
                startThreads();
                joinThreads();
                long end = System.currentTimeMillis();
                sum += (end - start);
            }
            //Printa o número de leitores, escritores e média do tempo gasto nas 50 execuções
            System.out.println(i + "," + (100 - i) + "," + sum/50 +",");
            sum = 0;
        }
    }

    private static void createThreads(Implementations implementation, int proportion) {
        threads = new Thread[100];
        ArrayList<Integer> indexes = new ArrayList();
        Random random = new Random();

        //Popula o vetor de indíces
        for (int i = 0; i < 100; i++){
            indexes.add(Integer.valueOf(i));
        }

        //Insere escritores em posicões aleatórias do arranjo de Threads
        for (int j = 0; j < proportion; j++) {
            int index = indexes.remove(random.nextInt(indexes.size()));
            threads[index] = new Thread(new Writer(readerWriter, lock, implementation));
        }

        //Insere um leitores em posicões aleatórias do arranjo de Threads
        for (int j = proportion; j < 100; j++) {
            int index = indexes.remove(random.nextInt(indexes.size()));
            threads[index] = new Thread(new Reader(readerWriter, lock, implementation));
        }

    }

    private static void startThreads(){
        for (int i = 0; i < threads.length; i++) {
            threads[i].start(); //Impede que a Thread main termine antes das outras threads
        }
    }

    private static void joinThreads() {
        try {
            for (int i = 0; i < threads.length; i++) {
                threads[i].join(); //Impede que a Thread main termine antes das outras threads
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
