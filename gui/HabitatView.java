package gui;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import model.Car;
import model.Truck;
import repository.TransportRepository;
import service.SimulationService;

public class HabitatView extends Pane {
  private Car car;
  private Truck truck;

  private TransportRepository repository;

  private SimulationService simulationService; // ← добавить
  private boolean showTime = true; // ← добавить

  public HabitatView(TransportRepository repository) {
    this.repository = repository;
  }

  
  
  public void addCar(Car car) {
    if (car == null || car.getImageView() == null) {
        System.err.println("Ошибка: car или его ImageView равны null");
        return;
    }
    Platform.runLater(() -> {
        this.getChildren().add(car.getImageView());
    });
}
    
  

  public void addTruck(Truck truck) {
    if (truck == null || truck.getImageView() == null) {
        System.err.println("Ошибка: грузовик или его ImageView равны null");
        return;
    }
    Platform.runLater(() -> {
        this.getChildren().add(truck.getImageView());
    });
}

  public void removeCar(Car car) {
    if (car == null) return;
    ImageView iv = car.getImageView();
    if (iv == null) {return;
    }
        car.clearImageView();   // ← разрываем связь
        Platform.runLater(() -> {
            if (iv.getParent() != null) {
                this.getChildren().remove(iv);
            }
        });
    
}

  public void setSimulationService(SimulationService service) {
    this.simulationService = service;
  }

  public void setShowTime(boolean show) {
    this.showTime = show;
  }

  public void setRepository(TransportRepository repository) {
    this.repository = repository;
  }
  public void removeTruck(Truck truck) {
    if (truck == null) return;
    ImageView iv = truck.getImageView();
    if (iv == null) {return;
    }
        truck.clearImageView();   // ← разрываем связь
        Platform.runLater(() -> {
            if (iv.getParent() != null) {
                this.getChildren().remove(iv);
            }
        });
    
}
}
