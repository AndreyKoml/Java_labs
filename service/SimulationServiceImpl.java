package service;

import alfragment.CarAI;
import alfragment.TruckAI;
import gui.HabitatView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import model.Car;
import model.Transport;
import model.TransportType;
import model.Truck;
import repository.TransportRepository;
import alfragment.BaisAIimp;
import util.Logger;

// import alfragment.TruckAI;

public class SimulationServiceImpl implements SimulationService {
  private final TransportRepository repository;
  private final Random random = new Random();
  private AnimationTimer timer; // ← добавить поле

  private long startTime;
  private long lastTruckTime;
  private long lastCarTime;
  private boolean isRun = false;
  private boolean showTime = true;

  private int nCar, nTruck;
  private double pCar, pTruck;
  private long carlifetime, trucklifetime;
  private HabitatView view;
  private CarAI carAI;
  private TruckAI truckAI;
  private int totalCarsCreated = 0;
private int totalTrucksCreated = 0;
private final int MAX_CARS = 10;
  
  boolean cartread;
  boolean truckthread;
  private boolean isPausedCar = false;
  private boolean isPausedTruck = false;
  private double areaWidth;
private double areaHeight;


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
  public void start(
      int nCar,
      int nTruck,
      double pCar,
      double pTruck,
      long carlifetime,
      long trucklifetime,
      boolean carthread,
      boolean truckthread,double width, double height


      ) {
        
    System.out.println("SimulationServiceImpl.start()");
    this.nCar = nCar;
    this.nTruck = nTruck;
    this.pCar = pCar;
    this.pTruck = pTruck;
    this.carlifetime = carlifetime;
    this.trucklifetime = trucklifetime;
    this.cartread = carthread;
    this.truckthread = truckthread;
    this.areaWidth = width;
    this.areaHeight = height;

    repository.clean();

    starttimer();
    if (carAI == null && carthread == true) {
      carAI = new CarAI(repository);
      carAI.start();
    }
    if (truckAI == null && truckthread == true) {
      truckAI = new TruckAI(repository);
      truckAI.start();
    }
  }

  @Override
  public void stop() {
    isRun = false;
    if (timer != null) {
      timer.stop();
    }
    if (this.carAI != null) {
    this.carAI.stop(); 
    }
    if (this.truckAI != null) {
        this.truckAI.stop();
    }
    if (this.timer != null) {
        this.timer.stop();
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
  }

  public void starttimer() {
    this.startTime = System.currentTimeMillis();
    this.lastCarTime = 0;
    this.lastTruckTime = 0;
    this.isRun = true;
    if (this.timer != null) {
      timer.stop();
    }
    this.timer = new AnimationTimer() {
          @Override
          public void handle(long now) {
            update(); // ← вызываем update() каждый кадр
          }
        };
    timer.start();
  }

  public void update_for_remove() {
    List<Transport>toremove=new ArrayList<>();
    long currentTime = (System.currentTimeMillis() - startTime) / 1000;
    synchronized(repository){
    for (Transport t : repository.getAll()) {
      if (currentTime - t.getbirthtime() >= t.getlifetime()) {
        if (t instanceof Car && isPausedCar) continue;
        if (t instanceof Truck && isPausedTruck) continue;
        toremove.add(t);
      }
    }
  }
    for (Transport t : toremove) {
    repository.remove(t);   // ← сначала из репозитория
}
for (Transport t : toremove) {
    if (t instanceof Car) view.removeCar((Car) t);
    else if (t instanceof Truck) view.removeTruck((Truck) t);
}
  }

  public void update_for_add() {
    long elapsed = System.currentTimeMillis() - startTime;
    // Легковые
    if(!isPausedCar){
    if (elapsed - lastCarTime >= nCar * 1000) {
      lastCarTime = elapsed; // ← ВСЕГДА обновляем
      if (random.nextDouble() < pCar) {
        if (view != null) generateCar();
      }
      }
    }
    // Грузовики
    if(!isPausedTruck){
    if (elapsed - lastTruckTime >= nTruck * 1000) {
      lastTruckTime = elapsed; // ← ВСЕГДА обновляем

      if (random.nextDouble() < pTruck) {
        if (view != null) generateTruck();
        System.out.println("view.refresh() вызван");
      }
    }
  }
  }
public void generateCar() {
    long birthTime = (System.currentTimeMillis() - startTime) / 1000;
    int id = generateId();
    
    // Запасные размеры экрана, если область view еще не просчитана
    double width = (this.areaWidth > 0) ? this.areaWidth : 1800;
    double height = (this.areaHeight > 0) ? this.areaHeight : 900;
    
    // Генерируем только начальную позицию (в пределах экрана, учитывая размеры машины)
    double x = random.nextDouble(0, width - 80); 
    double y = random.nextDouble(0, height - 50);
    
    Car car = new Car(x, y, birthTime, carlifetime, id);
    
    // Сразу привязываем картинку к начальной точке
    if (car.getImageView() != null) {
        car.getImageView().setX(x);
        car.getImageView().setY(y);
    }
    
    Logger.CarGenerate("generateCar: .setX( " + x + " ), setY(" + y + ")");
    Logger.car("new Car , id=" + car.getid() + ", brighttime=" + car.getbirthtime());
    repository.add(car);
    
    if (view != null) {
        view.addCar(car);
    }
    System.out.println("generateCar: координаты x=" + x + ", y=" + y);
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

  

  
    
    public void generateTruck() {
    long birthTime = (System.currentTimeMillis() - startTime) / 1000;
    int id = generateId();
    
    // Запасные размеры экрана, если область view еще не просчитана
    double width = (this.areaWidth > 0) ? this.areaWidth : 1920;
    double height = (this.areaHeight > 0) ? this.areaHeight : 1080;
    
    // Генерируем только начальную позицию (в пределах экрана)
    double x = random.nextDouble(0, width - 100); 
    double y = random.nextDouble(0, height - 60);
    
    Truck truck = new Truck(x, y, birthTime, trucklifetime, id);
    
    // Сразу привязываем картинку к начальной точке
    if (truck.getImageView() != null) {
        truck.getImageView().setX(x);
        truck.getImageView().setY(y);
    }
    
    Logger.truck("new Truck , id=" + truck.getid() + ", brighttime=" + truck.getbirthtime());
    repository.add(truck);
    
    if (view != null) {
        view.addTruck(truck);
    }
  }

  
      
      
  
  
  public void removeTruck(Truck t) {
    if (t != null && t.getImageView() != null) {
    view.removeTruck(t);
    }
  }
      
    
    
  
  @Override
  public void toggleCarPause(boolean pause){carAI.setpause(pause);}
  public void toggleTruckPause(boolean pause){truckAI.setpause(pause);}
  public boolean recipientCarPaused(){return carAI != null && carAI.getPaused();}
  public boolean recipientTruckPaused(){return truckAI != null && truckAI.getPaused();}
  public boolean recipientCarRunning(){return carAI.getrunning();}
  public void carStart(){carAI.start();}
  
  
  
  public boolean recipientTruckRunning(){return truckAI.getrunning();}
  public void truckStart(){truckAI.start();}
  @Override
  public void resumeTruckAI() {
    if (truckAI != null) {
        truckAI.resume(); 
        isPausedTruck = false;  // ← вызываем resume из BaisAIimp
    }
} 
public void pauseTruckAI() {
    if (truckAI != null) {
        truckAI.pause();
        isPausedTruck = true;    // ← вызываем pause из BaisAIimp
    }
}
public void pauseCarAI() {
    if (carAI != null) {
        carAI.pause();
        isPausedCar = true;    // ← вызываем pause из BaisAIimp
    }
}
public void resumeCarAI() {
    if (carAI != null) {
        carAI.resume(); 
        isPausedCar = false;  // ← вызываем resume из BaisAIimp
    }
} 
}
