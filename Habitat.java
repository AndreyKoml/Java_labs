package cartrucksimulation;

import javax.swing.*;
import java.awt.*; //Для draw
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Habitat extends JPanel{
    
    private ArrayList<Transport> transports;
                private javax.swing.Timer timer;
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
                    
                   timer = new Timer(100, newActionListener(){
                        
                        @Override
                        public void actionPerformed(ActionEvent e){
                            update();
                        }}
                    });
                   
                    private void update() {
                   
                       long currentTime = System.currentTimeMillis();
                       long timeFinish = currentTime - startTime;
                       
                       //Для легковой машины
                       if (timeFinish - lastTimeCarGenerated >= N2_Car * 1000) {
                           
                           lastTimeCarGenerated = timeFinish;
                           
                           if (random.nextDouble() < P2_Car) {
                               
                               transports.add(new Car(random.nextInt(getWidth() - 70), random.nextInt(getHeight() - 40)));
                           }
                       }
                       
                       repaint();
                   }
                   
                   addKeyListener(new KeyAdapter(){
                       
                       @Override
                       public void keyPressed(KeyEvent e){
                           switch (e.getKeyCode()){
                               case KeyEvent.VK_B: startSimulation();
                               break();
                               case KeyEvent.VK_E: stopSimulation();
                               break();
                               case KeyEvent.VK_T: if(isRun){
                               showTime = !showTime;
                               repaint();
                               }
                               break();
                           }
                       }}
                           
                    
                   }});
                   
                   private void startSimulation(){
                       
                       if (isRun) return;
                       transports.clear();
                       isRun = true;
                       showStatistic = false;
                       startTime = System.currentTimeMillis();
                       lastTimeCarGenerated = 0;
                       lastTimeTruckGenerated = 0;
                       timer.start();
                   }
                   
                   private void stopSimulatoin(){
                       
                       if (!isRun) return;
                       timer.stop();
                       isRun = false;
                       showStatistic = true;
                       repaint();
                   }
}
