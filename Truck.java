package cartrucksimulation;

import java.awt.Color;
import java.awt.Graphics;

public class Truck extends Transport{
    
     public Truck(int x, int y){super(x,y);
                
                }
                
                @Override
                public void draw(Graphics g){
                    g.setColor(Color.yellow);
                    g.fillRect(x, y, 80, 40);
                    g.setColor(Color.black);
                    g.fillRect(x + 80, y + 15, 25, 25);
                    g.fillOval(x + 10, y + 35, 12, 12);
                    g.fillOval(x + 50, y + 35, 12, 12);
                    g.fillOval(x + 85, y + 35, 12, 12);
                    g.setColor(Color.yellow);
                    g.drawString("Truck", x + 10, y + 25);
                        
            }
                
                 @Override
                    public String getType(){
                    
                    return "Грузовая";
                }
        }

