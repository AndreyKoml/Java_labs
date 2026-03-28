//package cartrucksimulation;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.Random;

public class HabitatFX extends Pane {
    private int N_Car = 1;
    private int N_Truck = 2;
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer timer;
    
    private long startTime;
    private boolean isRun = false;
    private boolean showTime = true;

    private double P_Car, P_Truck;
 
    public HabitatFX() {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        
        timer = new AnimationTimer() {
            private long lastCarGen = 0;
            private long lastTruckGen = 0;
            
            @Override
            public void handle(long now) {
                if (!isRun) return;
                
                long currentTime = System.currentTimeMillis();
                long elapsed = currentTime - startTime;
                
                // Генерация легковых 
                if (elapsed - lastCarGen >= N_Car * 1000) {
                    lastCarGen = elapsed;
                    generateCar();
                }
                
                // Генерация грузовиков
                if (elapsed - lastTruckGen >= N_Truck * 1000) {
                    lastTruckGen = elapsed;
                    generateTruck();
                }
                
                draw();
            }
        };
    }

    public void startSimulation(int nCar, int nTruck, double pCar, double pTruck) {
        this.N_Car = nCar;
        this.N_Truck = nTruck;
        this.P_Car = pCar;
        this.P_Truck = pTruck;
        
        TransportCollection.getInstance().clear();
        startTime = System.currentTimeMillis();
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

    private void generateCar() {
        Random r = new Random();
        if (r.nextDouble() < P_Car) {
            int x = r.nextInt((int)canvas.getWidth() - 60);
            int y = r.nextInt((int)canvas.getHeight() - 40);
            TransportCollection.getInstance().getTransports().add(
                new Car(x, y)
            );
        }
    }

    private void generateTruck() {
        Random r = new Random();
        if (r.nextDouble() < P_Truck) {
            int x = r.nextInt((int)canvas.getWidth() - 100);
            int y = r.nextInt((int)canvas.getHeight() - 50);
            TransportCollection.getInstance().getTransports().add(
                new Truck(x, y)
            );
        }
    }

    private void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        for (Transport t : TransportCollection.getInstance().getTransports()) {
            t.draw(gc);
        }
        
        if (showTime && isRun) {
            long currentSimTime = (System.currentTimeMillis() - startTime) / 1000;
            gc.setFill(Color.RED);
            gc.fillText("Время: " + currentSimTime + " сек", 10, 20);
        }
    }

    public boolean isRun() { return isRun; }
    public void setShowTime(boolean show) { this.showTime = show; }
    public long getCurrentSimTime() { return (System.currentTimeMillis() - startTime) / 1000; }
    public boolean isShowTime() {
    return showTime;
}
}