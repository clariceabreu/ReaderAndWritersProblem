package com.clariceabreu.entities;

import com.clariceabreu.enums.Implementations;
import com.clariceabreu.enums.Priorities;
import com.clariceabreu.utils.CriticalArea;
import com.clariceabreu.utils.Lock;
import com.clariceabreu.utils.ReaderWriter;

public class Reader implements Runnable {
    private Lock lock;
    private ReaderWriter readerWriter;
    private Implementations implementation;

    public Reader(ReaderWriter readerWriter, Lock lock, Implementations implementation) {
        this.readerWriter = readerWriter;
        this.implementation = implementation;
        this.lock = lock;
    }

    @Override
    public void run() {
        if(implementation == Implementations.READER_PRIORITY) runReaderWriter();
        else if (implementation == Implementations.WRITER_PRIORITY) {
            readerWriter.priority = Priorities.WRITERS;
            runReaderWriter();
        }
        else runLock();
    }

    private void runLock() {
        lock.lock();
        accessCriticalArea();
        lock.unlock();
    }

    private void runReaderWriter() {
        readerWriter.startReaderAccess();
        accessCriticalArea();
        readerWriter.stopReaderAccess();
    }

    //Acessa a região crítica e dorme por 1 ms
    private void accessCriticalArea() {
        CriticalArea.readerAccess();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
