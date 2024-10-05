package org.example.gof.patterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static void main(String[] args) {
        //create subject
        MyTopic topic = new MyTopic();

        //create observers
        MyObserver obj1 = new MyTopicSubscriber("Obj1");
        MyObserver obj2 = new MyTopicSubscriber("Obj2");
        MyObserver obj3 = new MyTopicSubscriber("Obj3");

        //register observers to the subject
        topic.register(obj1);
        topic.register(obj2);
        topic.register(obj3);

        //attach observer to subject
        obj1.setSubject(topic);
        obj2.setSubject(topic);
        obj3.setSubject(topic);

        //check if any update is available
        obj1.update();

        //now send message to subject
        topic.postMessage("New Message");
    }
}

interface Subject {

    //methods to register and unregister observers
    public void register(MyObserver obj);
    public void unregister(MyObserver obj);

    //method to notify observers of change
    public void notifyObservers();

    //method to get updates from subject
    public Object getUpdate(MyObserver obj);

}

interface MyObserver {

    //method to update the observer, used by subject
    public void update();

    //attach with subject to observe
    public void setSubject(Subject sub);
}

class MyTopic implements Subject {

    private List<MyObserver> observers;
    private String message;
    private boolean changed;
    private final Object MUTEX= new Object();

    public MyTopic(){
        this.observers=new ArrayList<>();
    }

    @Override
    public void register(MyObserver obj) {
        if(obj == null) throw new NullPointerException("Null Observer");
        synchronized (MUTEX) {
            if(!observers.contains(obj)) observers.add(obj);
        }
    }

    @Override
    public void unregister(MyObserver obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        List<MyObserver> observersLocal = null;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed)
                return;
            observersLocal = new ArrayList<>(this.observers);
            this.changed=false;
        }
        for (MyObserver obj : observersLocal) {
            obj.update();
        }

    }

    @Override
    public Object getUpdate(MyObserver obj) {
        return this.message;
    }

    //method to post message to the topic
    public void postMessage(String msg){
        System.out.println("Message Posted to Topic:"+msg);
        this.message=msg;
        this.changed=true;
        notifyObservers();
    }

}

class MyTopicSubscriber implements MyObserver {

    private String name;
    private Subject topic;

    public MyTopicSubscriber(String nm){
        this.name=nm;
    }

    @Override
    public void update() {
        String msg = (String) topic.getUpdate(this);
        if(msg == null){
            System.out.println(name+":: No new message");
        }else
            System.out.println(name+":: Consuming message::"+msg);
    }

    @Override
    public void setSubject(Subject sub) {
        this.topic=sub;
    }

}
