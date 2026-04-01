package service;

public interface SimulationService {
    void start(int nCar,int nTruck,double pCar,double pTruck);
    void stop();
    void resume();
    void update();
    long getCurrentTime();
    boolean isRun();
}
