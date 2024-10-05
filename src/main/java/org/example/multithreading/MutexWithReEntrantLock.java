package org.example.multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexWithReEntrantLock {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        WorkerThread thread1 = new WorkerThread(sharedResource, "Thread-1");
        WorkerThread thread2 = new WorkerThread(sharedResource, "Thread-2");
        WorkerThread thread3 = new WorkerThread(sharedResource, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final Count: " + sharedResource.getCount());
    }
}

class SharedResource {
    private int count = 0;
    private final Lock mutex = new ReentrantLock();

    public void increment() {
        mutex.lock(); // Acquire lock
        try {
            count++;
            System.out.println("Thread " + Thread.currentThread().getName() + ": Count = " + count);
        } finally {
            mutex.unlock(); // Release lock
        }
    }

    public int getCount() {
        return count;
    }
}

class WorkerThread extends Thread {
    private final SharedResource sharedResource;

    public WorkerThread(SharedResource sharedResource, String name) {
        super(name);
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            sharedResource.increment();
        }
    }
}


