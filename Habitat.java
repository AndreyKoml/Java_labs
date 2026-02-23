package cartrucksimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Habitat extends JPanel {
    private ArrayList<Transport> transports;
    private Timer timer;
    private long startTime;
    private long lastTimeCarGenerated = 0;
    private long lastTimeTruckGenerated = 0;
    
    private boolean isRunning = false;
    private boolean showTime = true;
    private boolean showStats = false; 

    // Параметры симуляции
    private final int N1_Truck = 2; 
    private final double P1_Truck = 0.6;
    private final int N2_Car = 1; 
    private final double P2_Car = 0.9;

    private Random random;

    public Habitat() {
        this.transports= new ArrayList<>();
        this.random = new Random();
        
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true); 

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_B: startSimulation(); break;
                    case KeyEvent.VK_E: stopSimulation(); break;
                    case KeyEvent.VK_T: 
                        if (isRunning) {
                            showTime = !showTime;
                            repaint();
                        }
                        break;
                }
            }
        });
    }

    private void startSimulation() {
        if (isRunning) return;
        transports.clear();
        isRunning = true;
        showStats = false;
        startTime = System.currentTimeMillis();
        lastTimeCarGenerated = 0;
        lastTimeTruckGenerated = 0;
        timer.start();
    }

    private void stopSimulation() {
        if (!isRunning) return;
        timer.stop();
        isRunning = false;
        showStats = true;
        repaint();
    }

    private void update() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - startTime;

        if (timeElapsed - lastTimeTruckGenerated >= N1_Truck * 1000) {
            lastTimeTruckGenerated = timeElapsed;
            if (random.nextDouble() < P1_Truck) {
                int rx = random.nextInt(Math.max(1, getWidth() - 100));
                int ry = random.nextInt(Math.max(1, getHeight() - 50));
                transports.add(new Truck(rx, ry));
            }
        }

        if (timeElapsed - lastTimeCarGenerated >= N2_Car * 1000) {
            lastTimeCarGenerated = timeElapsed;
            if (random.nextDouble() < P2_Car) {
                int rx = random.nextInt(Math.max(1, getWidth() - 70));
                int ry = random.nextInt(Math.max(1, getHeight() - 40));
                transports.add(new Car(rx, ry));
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Transport v : transports) {
            v.draw(g);
        }

        if (isRunning && showTime) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Время: " + (System.currentTimeMillis() - startTime) / 1000 + " сек", 10, 20);
        }

        if (showStats) {
            drawStats(g);
        }
    }

    private void drawStats(Graphics g) {
        int carCount = 0;
        int truckCount = 0;
        for (Transport v : transports) {
            if (v instanceof Car) carCount++;
            if (v instanceof Truck) truckCount++;
        }

        int boxW = 400; int boxH = 200;
        int boxX = (getWidth() - boxW) / 2;
        int boxY = (getHeight() - boxH) / 2;

        g.setColor(new Color(255, 255, 255, 220));
        g.fillRect(boxX, boxY, boxW, boxH);

        g.setColor(Color.BLACK);
        g.drawRect(boxX, boxY, boxW, boxH);

        g.setFont(new Font("Serif", Font.BOLD, 22));
        g.setColor(Color.BLUE);
        g.drawString("Статистика симуляции", boxX + 80, boxY + 30);

        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.setColor(Color.DARK_GRAY);
        g.drawString("Легковые авто: " + carCount, boxX + 40, boxY + 70);
        g.setColor(new Color(150, 0, 0));
        g.drawString("Грузовые авто: " + truckCount, boxX + 40, boxY + 100);
        
        g.setFont(new Font("Arial", Font.ITALIC, 14));
        g.setColor(Color.BLACK);
        g.drawString("Всего объектов: " + (carCount + truckCount), boxX + 40, boxY + 140);
        g.drawString("Нажмите 'B' для рестарта", boxX + 100, boxY + 180);
    }
}
