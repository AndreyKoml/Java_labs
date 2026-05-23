package alfragment;
import model.Truck;
import model.Car;
import model.Transport;
import model.TransportType;
import repository.TransportRepository;

import util.Logger;

import alfragment.Brain;
import javafx.scene.image.ImageView;
public class TruckAI extends BaisAIimp {
 private Brain brain = new Brain(); 
  public TruckAI(TransportRepository repository) {
    super(repository);
  }
  @Override
public void run() {
    super.run();
}
  @Override
public void move(Transport t) {
  ImageView iv = ((Truck) t).getImageView();
  if (iv == null) return;

  // 1. ИСПРАВЛЕНО: Меняем && на || для железобетонного срабатывания "мозгов"
  if (t.getendX() == 10000 || t.getendY() == 10000) {
    double windowWidth = (iv.getScene() != null) ? iv.getScene().getWidth() : 1920;
    double windowHeight = (iv.getScene() != null) ? iv.getScene().getHeight() : 1080;
    brain.calkul(t, windowWidth, windowHeight);
  }

  // 2. Вычисляем новые координаты в логической модели (в фоновом потоке)
  double nextX = t.getx() + t.getdX();
  double nextY = t.gety() + t.getdY();
  
  t.setX(nextX);
  t.setY(nextY);

  // 3. ИСПРАВЛЕНО: Перенос картинки на экран делаем строго через Platform.runLater
  final double finalX = nextX;
  final double finalY = nextY;
  javafx.application.Platform.runLater(() -> {
    iv.setX(finalX);
    iv.setY(finalY);
  });

  // 4. Проверка достижения цели (Ваш отличный алгоритм, оставляем его)
  boolean reachedX = Math.abs(t.getx() - t.getendX()) < Math.abs(t.getdX());
  boolean reachedY = Math.abs(t.gety() - t.getendY()) < Math.abs(t.getdY());
  
  if (reachedX && reachedY) {
    t.setX(t.getendX());
    t.setY(t.getendY());
    t.setdX(0);
    t.setdY(0);
    Logger.TruckGetEnd("Truck ID: " + t.getid() + " Truck:завершился");
  }
}
  @Override
  public boolean isMyType(Transport t) {
    boolean result = t instanceof Truck;
    return result;
  }
}