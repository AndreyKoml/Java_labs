package cartrucksimulation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Habitat extends JPanel {
    
    private Timer timerCar;
    private Timer timerTruck;
    
    private long startTime;
    private boolean isRun = false;
    private boolean showTime = true;

    private double P_Car, P_Truck;

    public Habitat() {
        setBackground(Color.WHITE);
        setFocusable(true);

        timerCar = new Timer(1000, e -> generateCar());
        timerTruck = new Timer(1000, e -> generateTruck());
    }

    public void startSimulation(int nCar, int nTruck, double pCar, double pTruck) {
        this.P_Car = pCar;
        this.P_Truck = pTruck;
        
        TransportCollection.getInstance().clear();
        startTime = System.currentTimeMillis();
        isRun = true;

        timerCar.setInitialDelay(0);
        timerCar.setDelay(nCar * 1000);
        
        timerTruck.setInitialDelay(100); 
        timerTruck.setDelay(nTruck * 1000);

        timerCar.start();
        timerTruck.start();
    }

    public void stopSimulation() {
        isRun = false;
        timerCar.stop();
        timerTruck.stop();
    }

    public void resumeSimulation() {
        isRun = true;
        timerCar.start();
        timerTruck.start();
    }

    private void generateCar() {
        Random r = new Random();
        if (r.nextDouble() < P_Car) {
            TransportCollection.getInstance().getTransports().add(
                new Car(r.nextInt(getWidth() - 60), r.nextInt(getHeight() - 40))
            );
        }
        repaint();
    }

    private void generateTruck() {
        Random r = new Random();
        if (r.nextDouble() < P_Truck) {
            TransportCollection.getInstance().getTransports().add(
                new Truck(r.nextInt(getWidth() - 100), r.nextInt(getHeight() - 50))
            );
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Transport v : TransportCollection.getInstance().getTransports()) {
            v.draw(g);
        }
        if (showTime && isRun) {
            long currentSimTime = (System.currentTimeMillis() - startTime) / 1000;
            g.setColor(Color.BLACK);
            g.drawString("Время: " + currentSimTime + " сек", 10, 20);
        }
    }

    public boolean isRun() { return isRun; }
    public void setShowTime(boolean show) { this.showTime = show; repaint(); }
    public long getCurrentSimTime() { return (System.currentTimeMillis() - startTime) / 1000; }
}