package cartrucksimulation;

import javax.swing.*;
import java.awt.*; //Для draw
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
       

public class CarTruckSimulation {

    public static void main(String[] args) {
        
        interface IBehaviour {
            void move();
        }
        
        abstract class Transport implements IBehaviour {
            
            protected int x,y;
            
            public Transport(int x, int y){
                this.x = x;
                this.y = y;
            }
            public abstract void draw(Graphics g);
            public abstract String getType();
            
            @Override
            public void move(){
            }
            
            
            class Car extends Transport{
                
                public Car(int x, int y){super(x,y);
                
            }
                @Override
                public void draw(Graphics g){
                    g.setColor(Color.green);
                    g.fillRoundRect(x, y, 60, 30, 10, 10);
                    g.setColor(Color.black);
                    g.fillOval(x + 10, y + 25, 10, 10);
                    g.fillOval(x + 40, y + 25, 10, 10);
                    g.setColor(Color.green);
                    g.drawString("Car", x + 15, y + 20);
                    
                    /*@Override
                    public String getType(){
                    
                    return "Легковая";
                }*/ //пока не понял, почему перегрузка геттера не работает,
                //а без нее не работает производный класс с легковой машинкой Car
                }
                
            }
            
            class Truck extends Transport{
                
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
                
                 /*@Override
                    public String getType(){
                    
                    return "Грузовая";
                }*/
        }
            
            class Habitat extends JPanel { //Апплет
                
                private ArrayList<Transport> transports;
                private Timer timer;
                private long startTime;
                private long lastTimeCarGenerated = 0;
                private long lastTimeTruckGenerated = 0;
                
                private boolean isRun = false;
                private boolean showTime = true;
                private boolean showStatistic = false;
                
                private final int N2_Car = 2;
                private final double P2_Car = 0.7;
                
                private final int N1_Truck = 1;
                private final double P1_Truck = 0.3;
                
                private Random random;
                
                public Habitat(){
                    
                    this.transports = new ArrayList<> ();
                    this.random = new Random();
                    
                   /* timer = new Timer(100, newActionListener(){
                        
                        @Override
                        public void actionPerformed(ActionEvent e){
                            update();
                        }}
                    });*/
                   
                   addKeyListener(new KeyAdapter(){
                       
                       @Override
                       public void keyPressed(KeyEvent e){
                           switch (e.getKeyCode()){
                               case KeyEvent.VK_B: 
                           }
                       }}
                   }});
                   

                
            }
    }
    
}
