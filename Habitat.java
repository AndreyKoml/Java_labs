import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Habitat extends JPanel {
    private ArrayList<Transport> transports;
    private Timer timer;
    private long startTime;
    private long lastCarTime;
    private long lastTruckTime;
    private long currentSimTime;
    private boolean isRun = false;
    private boolean showTime = true;
    
    private int N_Car = 1;
    private int N_Truck = 2;
    private double P_Car = 0.5;
    private double P_Truck = 0.3;
    
    private Random random;
    
    public Habitat() {
        this.transports = TransportCollection.getInstance().getTransports();
        this.random = new Random();
        
        setBackground(Color.WHITE);
        setFocusable(true);
        
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }
    
    public void startSimulation(int nCar, int nTruck, double pCar, double pTruck) {
        this.N_Car = nCar;
        this.N_Truck = nTruck;
        this.P_Car = pCar;
        this.P_Truck = pTruck;
        
        TransportCollection.getInstance().clear();
        startTime = System.currentTimeMillis();
        lastCarTime = 0;
        lastTruckTime = 0;
        isRun = true;
        timer.start();
    }
    
    public void stopSimulation() {
        isRun = false;
        timer.stop();
    }
    
    public void resumeSimulation() {
        isRun = true;
        timer.start();
    }
    
    public boolean isRun() {
        return isRun;
    }
    
    public long getCurrentSimTime() {
        return currentSimTime;
    }
    
    public void setShowTime(boolean show) {
        this.showTime = show;
        repaint();
    }
    
    private void update() {
        currentSimTime = (System.currentTimeMillis() - startTime) / 1000;
        long elapsedMillis = System.currentTimeMillis() - startTime;
        
        if (elapsedMillis - lastCarTime >= N_Car * 1000) {
            lastCarTime = elapsedMillis;
            if (random.nextDouble() < P_Car) {
                int x = random.nextInt(Math.max(1, getWidth() - 60));
                int y = random.nextInt(Math.max(1, getHeight() - 40));
                transports.add(new Car(x, y));
            }
        }
        
        if (elapsedMillis - lastTruckTime >= N_Truck * 1000) {
            lastTruckTime = elapsedMillis;
            if (random.nextDouble() < P_Truck) {
                int x = random.nextInt(Math.max(1, getWidth() - 100));
                int y = random.nextInt(Math.max(1, getHeight() - 50));
                transports.add(new Truck(x, y));
            }
        }
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (Transport t : transports) {
            t.draw(g);
        }
        
        if (showTime && isRun) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Время: " + currentSimTime + " сек", 10, 20);
        }
    }
}