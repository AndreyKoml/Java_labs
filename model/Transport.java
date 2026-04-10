package model;
//package cartrucksimulation;

import javafx.scene.canvas.GraphicsContext;

public abstract class Transport implements TransportInt {
            
            protected int x,y,id;
            protected long lifetime,birthtime;
            
            public Transport(int x, int y, long birthtime, long lifetime, int id){
                this.x = x;
                this.y = y;
                this.lifetime=lifetime;
                this.birthtime=birthtime;
                this.id=id;
            }
            public abstract void draw(GraphicsContext gc);
            public abstract String getType();
            @Override
            public int getx(){return x;}
            @Override
            public int gety(){return y;}
            @Override
            public long getlifetime(){return lifetime;}
            @Override
            public long getbirthtime(){return birthtime;}
            @Override
            public int getid(){return id;}

            



}
