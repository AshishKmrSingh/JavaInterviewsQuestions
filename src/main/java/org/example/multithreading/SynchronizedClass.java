package org.example.multithreading;

public class SynchronizedClass {

    public static void main(String arg[])
    {
        Display d1 = new Display();
        Display d2 = new Display();
        MyThreadStaticSynchronized t1 = new MyThreadStaticSynchronized(d1,"Dhoni");
        MyThreadStaticSynchronized t2 = new MyThreadStaticSynchronized(d2,"Yuvaraj");
        t1.start();
        t2.start();
    }

}

// Java program of multithreading
// with static synchronized

class Display
{
    public static synchronized void wish(String name)
    {
        for(int i=0; i<3; i++)
        {
            System.out.print("Good Morning: ");
            System.out.println(name);
            try{
                Thread.sleep(2000);
            }
            catch(InterruptedException ignored)
            {
            }
        }
    }
}

class MyThreadStaticSynchronized extends Thread {

    Display d;

    String name;

    MyThreadStaticSynchronized(Display d,String name)
    {
        this.d = d;
        this.name = name;
    }

    @Override
    public void run()
    {
        Display.wish(name);
    }
}

