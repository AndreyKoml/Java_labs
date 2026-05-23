package service;

import gui.HabitatView;

public interface SimulationService {
  void start(
      int nCar,
      int nTruck,
      double pCar,
      double pTruck,
      long carlifetime,
      long trucklifetime,
      boolean carthread,
      boolean truckthread,double width, double height);

  void stop();

  void resume();

  void update();

  long getCurrentTime();

  boolean isRun();

  void setShowTime(boolean show);

  boolean isShowTime();

  void setView(HabitatView view);

  java.util.List<model.Transport> getAll();
  void toggleCarPause(boolean pause);
  void toggleTruckPause(boolean pause);
  boolean recipientCarPaused();
  boolean recipientCarRunning();
  void carStart();
  boolean recipientTruckPaused();
  boolean recipientTruckRunning();
  void truckStart();
  void resumeTruckAI();
  void resumeCarAI();
   void pauseCarAI();
   void pauseTruckAI();
}
