package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.HabitatView;
import javafx.animation.AnimationTimer;
import model.Car;
import model.Transport;
import model.Truck;
import repository.TransportRepository;


public class SimulationServiceImpl implements SimulationService {
    private final TransportRepository repository;
    private final Random random = new Random();
    private AnimationTimer timer;  // ← добавить поле
    

    private long startTime;
    private long lastTrackTime;
    private long lastCarTime;
    private boolean isRun = false;
    private boolean showTime = true;

    private int nCar, nTruck;
    private double pCar, pTruck;
    private long carlifetime,trucklifetime;
    private HabitatView view;
    public void setView(HabitatView view) {
    this.view = view;
    }
    private int nextId = 1;
        private int generateId() {
        return nextId++;
    }
    public SimulationServiceImpl(TransportRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void start(int nCar, int nTruck, double pCar, double pTruck,long carlifetime,long trucklifetime) {
        System.out.println("SimulationServiceImpl.start()");
        this.nCar = nCar;
        this.nTruck = nTruck;
        this.pCar = pCar;
        this.pTruck = pTruck;
        this.carlifetime=carlifetime;
        this.trucklifetime=trucklifetime;
        
        repository.clean();
        
        starttimer();
    }
    
    @Override
    public void stop() {
        isRun = false;
        if (timer != null) {
            timer.stop();
        }
    }
    
    @Override
    public void resume() {
        isRun = true;
        if (timer != null) {
            timer.start();
        }
    }
    
@Override
public void update() {
    if (!isRun) return;
    update_for_remove();
    update_for_add();
    if (view != null) view.refresh();
}
public void starttimer(){
    this.startTime = System.currentTimeMillis();
    this.lastCarTime = 0;
    this.lastTrackTime = 0;
    this.isRun = true;
    if (this.timer != null) {
        timer.stop();}
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();  // ← вызываем update() каждый кадр
            }
        };
        timer.start();
}
public void update_for_remove(){ 
    long currentTime = (System.currentTimeMillis() - startTime) / 1000;
    for (Transport t : repository.getAll()) {
        if (currentTime - t.getbirthtime() >= t.getlifetime()) {
            repository.remove(t);
            

    }   
    }
}
public void update_for_add(){
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("update() elapsed=" + elapsed + ", lastCarTime=" + lastCarTime);

    // Легковые
    if (elapsed - lastCarTime >= nCar * 1000) {
        lastCarTime = elapsed;  // ← ВСЕГДА обновляем
        
        if (random.nextDouble() < pCar) {
        long birthTime = (System.currentTimeMillis() - startTime) / 1000;
        int x = random.nextInt(740);
        int y = random.nextInt(560);
        int id = generateId();
        repository.add(new Car(x, y, birthTime, carlifetime, id));
        
        
            if (view != null) view.refresh();
                System.out.println("view.refresh() вызван");
        }
    }
    // Грузовики
    if (elapsed - lastTrackTime >= nTruck * 1000) {
    lastTrackTime = elapsed;
    
    if (random.nextDouble() < pTruck) {
        long birthTime = (System.currentTimeMillis() - startTime) / 1000;
        int x = random.nextInt(700);
        int y = random.nextInt(550);
        int id = generateId();
        
        repository.add(new Truck(x, y, birthTime, trucklifetime, id));
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
        return isRun;
    }
    
    @Override
    public void setShowTime(boolean show) {
        this.showTime = show;
    }
    
    @Override
    public boolean isShowTime() {
        return showTime;
    }
    @Override
public java.util.List<model.Transport> getAll() {
    return repository.getAll();
}
    


}