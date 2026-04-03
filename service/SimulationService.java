package service;

import gui.HabitatView;

public interface SimulationService {
    void start(int nCar,int nTruck,double pCar,double pTruck);
    void stop();
    void resume();
    void update();
    long getCurrentTime();
    boolean isRun();
    void setShowTime(boolean show);
    boolean isShowTime();
        void setView(HabitatView view);
        java.util.List<model.Transport> getAll();
}
