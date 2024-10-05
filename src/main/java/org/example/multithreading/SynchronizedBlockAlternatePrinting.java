package org.example.multithreading;

public class SynchronizedBlockAlternatePrinting {

    public static final int LIMIT = 100;

    public static void main(String[] args) {
        Monitor monitor = new Monitor(1);
        Thread t1 = new Thread(new MyThreadAlternatePrinter(1, monitor), "Thread-1");
        Thread t2 = new Thread(new MyThreadAlternatePrinter(2, monitor), "Thread-2");

        t1.start();
        t2.start();
    }
}

class MyThreadAlternatePrinter implements Runnable {

    public int index;

    public Monitor monitor;

    public MyThreadAlternatePrinter(int index, Monitor monitor) {
        this.index = index;
        this.monitor = monitor;
    }

    public void incrementIndexBy2() {
        this.index += 2;
    }

    @Override
    public void run() {
        synchronized(this.monitor) {
            while(this.monitor.currentCounter < 100) {
                while (this.monitor.currentCounter % this.index != 0) {
                    try {
                        this.monitor.wait();
                    } catch (InterruptedException ex) {
                        System.err.println("Thread interrupted");
                    }
                }
                System.out.println(Thread.currentThread().getName() + ":" + this.monitor.currentCounter);
                this.incrementIndexBy2();
                this.monitor.incrementCounter();
                this.monitor.notifyAll();
            }

        }
    }
}

class Monitor {
    public int currentCounter;

    public Monitor(int initialCounter) {
        this.currentCounter = initialCounter;
    }

    public void incrementCounter() {
        this.currentCounter++;
    }
}
