package com.clariceabreu.utils;

import com.clariceabreu.enums.Priorities;

public class ReaderWriter {
    public Priorities priority = Priorities.READERS;
    int readers = 0;
    int writers = 0;
    boolean hasWriter = false;

    //Permite acesso do leitor a região crítica
    //Quando a prioridade for de leitores permite acesso
    //Quando a prioridade for de escritores espera até que todos os escritores já tenham acessado a região crítica
    private boolean allowReaderAccess() {
        if (priority == Priorities.READERS) return true;
        else return writers == 0;
    }

    //Permite o acesso do escritor a região crítica
    //Quando a prioridade for de escritores permite acesso
    //Quando a prioridade for de leitores espera até que todos os leitores já tenham acessado a região crítica
    private boolean allowWriterAccess() {
        if (priority == Priorities.WRITERS) return true;
        else return readers == 0;
    }

    //Acesso do leitor a região crítica
    public synchronized void startReaderAccess() {
        readers++; //Incrementa o número de leitores esperando para entrar na região crítica
        while (!allowReaderAccess() || hasWriter) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Término do acesso do leitor a região crítica
    public synchronized void stopReaderAccess() {
        readers--;
        notifyAll();
    }

    //Acesso do escritor a região crítica
    public synchronized void startWriterAccess() {
        writers++; //Incrementa o número de escritores esperando para entrar na região crítica
        while (!allowWriterAccess() || hasWriter) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Enquanto um quando um escritor entra na região crítica outros objetos não podem entrar simultaneamente
        hasWriter = true;
    }

    //Término do acesso do leitor a região crítica
    public synchronized void stopWriterAccess() {
        hasWriter = false;
        writers--;
        notifyAll();
    }

}
