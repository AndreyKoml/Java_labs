package model;
//package cartrucksimulation;

import javafx.scene.canvas.GraphicsContext;

public abstract class Transport implements TransportInt {
            
            protected int x,y;
            
            
            public Transport(int x, int y){
                this.x = x;
                this.y = y;
            }
            public abstract void draw(GraphicsContext gc);
            public abstract String getType();
            @Override
            public int getx(){return x;}
            @Override
            public int gety(){return y;}



}
