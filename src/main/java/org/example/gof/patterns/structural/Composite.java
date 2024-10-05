package org.example.gof.patterns.structural;

import java.util.ArrayList;
import java.util.List;

public class Composite {

    public static void main(String[] args) {

        MyShape tri = new MyTriangle();
        MyShape tri1 = new MyTriangle();
        MyShape cir = new MyCircle();

        Drawing drawing = new Drawing();
        drawing.add(tri1);
        drawing.add(tri1);
        drawing.add(cir);

        drawing.draw("Red");

        drawing.clear();

        drawing.add(tri);
        drawing.add(cir);
        drawing.draw("Green");
    }
}

interface MyShape {

    public void draw(String fillColor);
}

class MyTriangle implements MyShape {

    @Override
    public void draw(String fillColor) {
        System.out.println("Drawing Triangle with color "+fillColor);
    }

}

class MyCircle implements MyShape {

    @Override
    public void draw(String fillColor) {
        System.out.println("Drawing Circle with color "+fillColor);
    }

}

class Drawing implements MyShape {

    //collection of Shapes
    private List<MyShape> shapes = new ArrayList<MyShape>();

    @Override
    public void draw(String fillColor) {
        for(MyShape sh : shapes)
        {
            sh.draw(fillColor);
        }
    }

    //adding shape to drawing
    public void add(MyShape s){
        this.shapes.add(s);
    }

    //removing shape from drawing
    public void remove(MyShape s){
        shapes.remove(s);
    }

    //removing all the shapes
    public void clear(){
        System.out.println("Clearing all the shapes from drawing");
        this.shapes.clear();
    }
}
