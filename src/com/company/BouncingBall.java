package com.company;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class BouncingBall implements Runnable{

    private static final int MAX_RADIUS = 100;
    public static final int MIN_RADIUS = 0;
    public static final int MAX_SPEED = 18;

    public Field field;
    public int radius;
    public Color color;

    private double x;
    private double y;

    private int speed;
    private final int mainSpeed;
    private int reduce;
    private final int mainReduce=1;
    private double speedX;
    private double speedY;



    public BouncingBall(Field field){

        this.field = field;
        radius = new Double(Math.random() * (MAX_RADIUS
                - MIN_RADIUS)).intValue() + MIN_RADIUS;

        speed = new Double(Math.round(5 * MAX_SPEED / radius)).intValue();
        if(speed>MAX_SPEED){
            speed = MAX_SPEED;
        }
        mainSpeed = speed;


        double angle = Math.random()*2*Math.PI;
        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);

        color = new Color((float)Math.random(), (float) Math.random(),
                (float) Math.random());

        x = Math.random()*(field.getSize().getWidth() - 2*radius)+radius;
        y = Math.random()*(field.getSize().getHeight() - 2*radius)+radius;

        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public void setReduce(double sandpaper){
        this.reduce = (int)(mainReduce * sandpaper);
        //this.reduce = reduce;
    }

    public void run(){
        try{
            while (true){
                field.canMove(this);
                if(x + speedX <= radius){
                    speedX = -speedX;
                    x = radius;
                    radius=radius-reduce;
                }else
                if(x + speedX >= field.getWidth() - radius){
                    speedX = -speedX;
                    x = new Double(field.getWidth() - radius).intValue();
                    radius=radius-reduce;
                }else
                if(y + speedY <= radius){
                    speedY = -speedY;
                    y = radius;
                    radius=radius-reduce;
                }else
                if (y + speedY >= field.getHeight() - radius){
                    speedY = -speedY;
                    y = new Double(field.getHeight()-radius).intValue();
                    radius=radius-reduce;
                }else{
                    x += speedX;
                    y += speedY;
                }
                Thread.sleep(18-speed);

            }
        }catch (InterruptedException ignored){
        }
    }

    public void paint(Graphics2D canvas){
        canvas.setColor(color);
        canvas.setPaint(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius,y-radius,
                2*radius,2*radius);
        canvas.draw(ball);
        canvas.fill(ball);
    }
}

