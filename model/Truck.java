package model;
//package cartrucksimulation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Truck extends Transport{
    
     public Truck(int x, int y){super(x,y);
                }
                
                @Override
                public void draw(GraphicsContext gc) {
    gc.setFill(Color.YELLOW);
    gc.fillRoundRect(x, y, 60, 30, 10, 10);
    gc.setFill(Color.BLACK);
    gc.fillOval(x + 10, y + 25, 10, 10);
    gc.fillOval(x + 40, y + 25, 10, 10);
    gc.setFill(Color.YELLOW);
    gc.fillText("Truck", x + 15, y + 20);
}
                
                 @Override
                    public String getType(){
                    
                    return "Грузовая";
                }
        }

