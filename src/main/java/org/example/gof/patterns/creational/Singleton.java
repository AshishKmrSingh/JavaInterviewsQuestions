package org.example.gof.patterns.creational;

public class Singleton {
}


class EagerInitializedSingleton {

    private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();

    // private constructor to avoid client applications using the constructor
    private EagerInitializedSingleton(){}

    public static EagerInitializedSingleton getInstance() {
        return instance;
    }
}


class StaticBlockSingleton {

    private static StaticBlockSingleton instance;

    private StaticBlockSingleton(){}

    // static block initialization for exception handling
    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}

class LazyInitializedSingleton {

    private static LazyInitializedSingleton instance;

    private LazyInitializedSingleton(){}

    public static LazyInitializedSingleton getInstance() {
        if (instance == null) {
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }
}

/* The preceding implementation works fine and provides thread-safety,
 but it reduces the performance because of the cost associated with the synchronized
  method, although we need it only for the first few threads that might create separate
   instances. To avoid this extra overhead every time, double-checked locking principle
    is used. In this approach, the synchronized block is used inside the if condition
     with an additional check to ensure that only one instance of a singleton class
     is created. The following code snippet provides the double-checked locking
     implementation.
 */

class ThreadSafeSingletonDoubleChecking {

    private static ThreadSafeSingletonDoubleChecking instance;

    private ThreadSafeSingletonDoubleChecking(){}

    public static ThreadSafeSingletonDoubleChecking getInstanceUsingDoubleLocking() {
        if (instance == null) {
            synchronized (ThreadSafeSingletonDoubleChecking.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingletonDoubleChecking();
                }
            }
        }
        return instance;
    }
}

