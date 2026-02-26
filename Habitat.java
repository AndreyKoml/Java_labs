package cartrucksimulation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Habitat extends JPanel {
    private Timer timer;
    private long startTime;
    private long lastCarTime, lastTruckTime;
    private long currentSimTime;
    private boolean isRun = false;
    private boolean showTime = true;

    private int N_Car = 1, N_Truck = 2;
    private double P_Car = 0.5, P_Truck = 0.5;

    public Habitat() {
        setBackground(Color.WHITE);
        setFocusable(true);
        timer = new Timer(100, e -> update());
    }

    public void startSimulation(int nC, int nT, double pC, double pT) {
        this.N_Car = nC; this.N_Truck = nT;
        this.P_Car = pC; this.P_Truck = pT;
        
        TransportCollection.getInstance().clear();
        startTime = System.currentTimeMillis();
        lastCarTime = 0; lastTruckTime = 0;
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

    private void update() {
        currentSimTime = (System.currentTimeMillis() - startTime) / 1000;
        long elapsedMillis = System.currentTimeMillis() - startTime;
        Random r = new Random();


        if (elapsedMillis - lastCarTime >= N_Car * 1000) {
            lastCarTime = elapsedMillis;
            if (r.nextDouble() < P_Car) {
                TransportCollection.getInstance().getTransports().add(new Car(r.nextInt(getWidth()-60), r.nextInt(getHeight()-40)));
            }
        }

        if (elapsedMillis - lastTruckTime >= N_Truck * 1000) {
            lastTruckTime = elapsedMillis;
            if (r.nextDouble() < P_Truck) {
                TransportCollection.getInstance().getTransports().add(new Truck(r.nextInt(getWidth()-100), r.nextInt(getHeight()-50)));
            }
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
            g.setColor(Color.BLACK);
            g.drawString("Время: " + currentSimTime + " сек", 10, 20);
        }
    }

    public boolean isRun() { 
        return isRun; 
    }
    public void setShowTime(boolean show) { 
        this.showTime = show; repaint(); 
    }
    public long getCurrentSimTime() { 
        return currentSimTime; 
    }
}