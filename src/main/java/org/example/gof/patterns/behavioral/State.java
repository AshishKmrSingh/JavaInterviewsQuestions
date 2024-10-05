package org.example.gof.patterns.behavioral;

public class State {
    public static void main(String[] args) {
        TVContext context = new TVContext();
        MyState tvStartState = new TVStartState();
        MyState tvStopState = new TVStopState();

        context.setState(tvStartState);
        context.doAction();

        context.setState(tvStopState);
        context.doAction();

    }
}


class TVRemoteBasic {

    private String state="";

    public void setState(String state){
        this.state=state;
    }

    public void doAction(){
        if(state.equalsIgnoreCase("ON")){
            System.out.println("TV is turned ON");
        }else if(state.equalsIgnoreCase("OFF")){
            System.out.println("TV is turned OFF");
        }
    }

    public static void main(String args[]){
        TVRemoteBasic remote = new TVRemoteBasic();

        remote.setState("ON");
        remote.doAction();

        remote.setState("OFF");
        remote.doAction();
    }

}

interface MyState {

    public void doAction();
}

class TVStartState implements MyState {

    @Override
    public void doAction() {
        System.out.println("TV is turned ON");
    }

}

class TVStopState implements MyState {

    @Override
    public void doAction() {
        System.out.println("TV is turned OFF");
    }

}

class TVContext implements MyState {

    private MyState tvState;

    public void setState(MyState state) {
        this.tvState=state;
    }

    public MyState getState() {
        return this.tvState;
    }

    @Override
    public void doAction() {
        this.tvState.doAction();
    }

}