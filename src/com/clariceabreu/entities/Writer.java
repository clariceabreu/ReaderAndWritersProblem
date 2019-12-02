package com.clariceabreu.entities;

import com.clariceabreu.enums.Implementations;
import com.clariceabreu.enums.Priorities;
import com.clariceabreu.utils.CriticalArea;
import com.clariceabreu.utils.Lock;
import com.clariceabreu.utils.ReaderWriter;

public class Writer implements Runnable {
    private Lock lock;
    private ReaderWriter readerWriter;
    private Implementations implementation;

    public Writer(ReaderWriter readerWriter, Lock lock, Implementations implementation) {
        this.readerWriter = readerWriter;
        this.implementation = implementation;
        this.lock = lock;
    }

    @Override
    public void run() {
        //Implementacão usando Readers e Writers priorizando leitores
        if(implementation == Implementations.READER_PRIORITY) runReaderWriter();

        //Implementacão usando Readers e Writers priorizando escritores
        else if (implementation == Implementations.WRITER_PRIORITY) {
            readerWriter.priority = Priorities.WRITERS;
            runReaderWriter();
        }

        //Implementacão bloqueando toda a área crítica a cada acesso
        else runLock();
    }

    private void runReaderWriter() {
        readerWriter.startWriterAccess();
        accessCriticalArea();
        readerWriter.stopWriterAccess();
    }

    private void runLock() {
        lock.lock();
        accessCriticalArea();
        lock.unlock();
    }

    //acessa a área crítica e dorme por 1 ms
    private void accessCriticalArea() {
        CriticalArea.writerAccess();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
