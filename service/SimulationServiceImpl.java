package service;

import java.util.Random;

import model.Car;
import model.Truck;
import repository.TransportRepository;

public class SimulationServiceImpl implements SimulationService {
    private final TransportRepository repository;
    private final Random random = new Random();

    private long startTime;
    private long lastTrackTime;
    private long lastCarTime;
    private boolean isRan = false;

    private int nCar,nTruck;
    private double pCar,pTruck;
    public SimulationServiceImpl(TransportRepository repository){
        this.repository=repository;
    }
    @Override
    public void start(int nCar,int nTruck,double pCar,double pTruck){
        this.nCar=nCar;
        this.nTruck=nTruck;
        this.pCar=pCar;
        this.pTruck=pTruck;

        repository.clean();
        startTime=System.currentTimeMillis();
        lastCarTime =0;
        lastTrackTime=0;
        isRan =true;
    }
    @Override
    public void stop(){
        isRan =true;
    }
    @Override
    public void resume(){
        isRan=true;
    }
    @Override
    public void update(){
        if(! isRan)return;
        long elapsed = System.currentTimeMillis()-startTime;

        if (elapsed - lastCarTime >= nCar * 1000){
            lastCarTime=elapsed;
        if (random.nextDouble() < pCar) {
            repository.add(new Car(random.nextInt(740), random.nextInt(560)));
        }
    }
        if (elapsed - lastTrackTime >= nTruck * 1000) {
        lastTrackTime = elapsed;
        if (random.nextDouble() < pTruck) {
            repository.add(new Truck(random.nextInt(700), random.nextInt(550)));
            }
        }
}
@Override
public long getCurrentTime(){
    return(System.currentTimeMillis()-startTime)/1000;
}
@Override
public boolean isRun(){
    return isRan;
}
}