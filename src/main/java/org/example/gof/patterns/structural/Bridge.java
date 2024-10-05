package org.example.gof.patterns.structural;

public class Bridge {

    public static void main(String[] args) {
        Shape tri = new Triangle(new RedColor());
        tri.applyColor();

        Shape pent = new Pentagon(new GreenColor());
        pent.applyColor();
    }

}

interface Color {

    public void applyColor();
}

class RedColor implements Color {

    public void applyColor(){
        System.out.println("red.");
    }
}

class GreenColor implements Color {

    public void applyColor(){
        System.out.println("green.");
    }
}

abstract class Shape {

    //Composition - implementor
    protected Color color;

    //constructor with implementor as input argument
    public Shape(Color c){
        this.color=c;
    }

    abstract public void applyColor();
}

class Triangle extends Shape{

    public Triangle(Color c) {
        super(c);
    }

    @Override
    public void applyColor() {
        System.out.print("Triangle filled with color ");
        color.applyColor();
    }

}

class Pentagon extends Shape{

    public Pentagon(Color c) {
        super(c);
    }

    @Override
    public void applyColor() {
        System.out.print("Pentagon filled with color ");
        color.applyColor();
    }

}
