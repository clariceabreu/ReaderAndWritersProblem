package com.clariceabreu.utils;

public class Lock {
    boolean locked = false;
    Thread lockedBy = null;
    int count = 0;

    //Se a região crítica está bloqueada por outra thread espera, se não a bloqueia
    public synchronized void lock() {
        try {
            Thread currentThread = Thread.currentThread();
            while (locked && lockedBy != currentThread) {
                wait();
            }
            locked = true;
            count++;
            lockedBy = currentThread;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Se a região crítica está bloqueada pela thread atual e o contador é igual a 0 desbloqueia e notifica
    //Se contador maior que 0 então apenas decrementa o contador
    public synchronized void unlock() {
        if (lockedBy == Thread.currentThread()) {
            count--;

            if (count == 0) {
                locked = false;
                notify();
            }
        }
    }
}
