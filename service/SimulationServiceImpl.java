package service;

import java.util.Random;

import gui.HabitatView;
import javafx.animation.AnimationTimer;
import model.Car;
import model.Truck;
import repository.TransportRepository;

public class SimulationServiceImpl implements SimulationService {
    private final TransportRepository repository;
    private final Random random = new Random();
    private AnimationTimer timer;  // ← добавить поле
    

    private long startTime;
    private long lastTrackTime;
    private long lastCarTime;
    private boolean isRan = false;
    private boolean showTime = true;

    private int nCar, nTruck;
    private double pCar, pTruck;
    private HabitatView view;
    public void setView(HabitatView view) {
    this.view = view;
}
    
    public SimulationServiceImpl(TransportRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void start(int nCar, int nTruck, double pCar, double pTruck) {
        System.out.println("SimulationServiceImpl.start()");
        this.nCar = nCar;
        this.nTruck = nTruck;
        this.pCar = pCar;
        this.pTruck = pTruck;

        repository.clean();
        startTime = System.currentTimeMillis();
        lastCarTime = 0;
        lastTrackTime = 0;
        isRan = true;
        
        // ЗАПУСКАЕМ ТАЙМЕР
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();  // ← вызываем update() каждый кадр
            }
        };
        timer.start();
    }
    
    @Override
    public void stop() {
        isRan = false;
        if (timer != null) {
            timer.stop();
        }
    }
    
    @Override
    public void resume() {
        isRan = true;
        if (timer != null) {
            timer.start();
        }
    }
    
@Override
public void update() {
    if (!isRan) return;
    long elapsed = System.currentTimeMillis() - startTime;
    System.out.println("update() elapsed=" + elapsed + ", lastCarTime=" + lastCarTime);

    // Легковые
    if (elapsed - lastCarTime >= nCar * 1000) {
        lastCarTime = elapsed;  // ← ВСЕГДА обновляем
        
        if (random.nextDouble() < pCar) {
            repository.add(new Car(random.nextInt(740), random.nextInt(560)));
            if (view != null) view.refresh();
                System.out.println("view.refresh() вызван");
        }
    }
    
    // Грузовики
    if (elapsed - lastTrackTime >= nTruck * 1000) {
        lastTrackTime = elapsed;  // ← ВСЕГДА обновляем
        
        if (random.nextDouble() < pTruck) {
            repository.add(new Truck(random.nextInt(700), random.nextInt(550)));
            if (view != null) view.refresh();
        }
    }
}
            
        
    
    
    @Override
    public long getCurrentTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    
    @Override
    public boolean isRun() {
        return isRan;
    }
    
    @Override
    public void setShowTime(boolean show) {
        this.showTime = show;
    }
    
    @Override
    public boolean isShowTime() {
        return showTime;
    }
}