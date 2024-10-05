package org.example.gof.patterns.structural;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.HashMap;

public class Flyweight {
    public static void main(String[] args) {
        DrawingClient drawing = new DrawingClient(500,600);
    }
}

interface FlyWeightShape {

    public void draw(Graphics g, int x, int y, int width, int height,
                     Color color);
}

class Line implements FlyWeightShape {

    public Line(){
        System.out.println("Creating Line object");
        //adding time delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics line, int x1, int y1, int x2, int y2,
                     Color color) {
        line.setColor(color);
        line.drawLine(x1, y1, x2, y2);
    }

}

class Oval implements FlyWeightShape {

    //intrinsic property
    private boolean fill;

    public Oval(boolean f){
        this.fill=f;
        System.out.println("Creating Oval object with fill="+f);
        //adding time delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics circle, int x, int y, int width, int height,
                     Color color) {
        circle.setColor(color);
        circle.drawOval(x, y, width, height);
        if(fill){
            circle.fillOval(x, y, width, height);
        }
    }

}

class ShapeFactory {

    private static final HashMap<ShapeType,FlyWeightShape> shapes = new HashMap<>();

    public static FlyWeightShape getShape(ShapeType type) {
        FlyWeightShape shapeImpl = shapes.get(type);

        if (shapeImpl == null) {
            if (type.equals(ShapeType.OVAL_FILL)) {
                shapeImpl = new Oval(true);
            } else if (type.equals(ShapeType.OVAL_NOFILL)) {
                shapeImpl = new Oval(false);
            } else if (type.equals(ShapeType.LINE)) {
                shapeImpl = new Line();
            }
            shapes.put(type, shapeImpl);
        }
        return shapeImpl;
    }

    public enum ShapeType{
        OVAL_FILL,OVAL_NOFILL,LINE;
    }
}

class DrawingClient extends JFrame {

    private static final long serialVersionUID = -1350200437285282550L;
    private final int WIDTH;
    private final int HEIGHT;

    private static final ShapeFactory.ShapeType[] shapes = { ShapeFactory.ShapeType.LINE, ShapeFactory.ShapeType.OVAL_FILL, ShapeFactory.ShapeType.OVAL_NOFILL };
    private static final Color[] colors = { Color.RED, Color.GREEN, Color.YELLOW };

    public DrawingClient(int width, int height){
        this.WIDTH=width;
        this.HEIGHT=height;
        Container contentPane = getContentPane();

        JButton startButton = new JButton("Draw");
        final JPanel panel = new JPanel();

        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(startButton, BorderLayout.SOUTH);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        startButton.addActionListener(event -> {
            Graphics g = panel.getGraphics();
            for (int i = 0; i < 20; ++i) {
                FlyWeightShape shape = ShapeFactory.getShape(getRandomShape());
                shape.draw(g, getRandomX(), getRandomY(), getRandomWidth(),
                        getRandomHeight(), getRandomColor());
            }
        });
    }

    private ShapeFactory.ShapeType getRandomShape() {
        return shapes[(int) (Math.random() * shapes.length)];
    }

    private int getRandomX() {
        return (int) (Math.random() * WIDTH);
    }

    private int getRandomY() {
        return (int) (Math.random() * HEIGHT);
    }

    private int getRandomWidth() {
        return (int) (Math.random() * (WIDTH / 10));
    }

    private int getRandomHeight() {
        return (int) (Math.random() * (HEIGHT / 10));
    }

    private Color getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }
}
