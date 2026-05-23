package alfragment;

import model.Car;
import model.Transport;
import model.TransportType;
import model.Truck;
import repository.TransportRepository;
import alfragment.Brain;
import util.Logger;
import javafx.scene.image.ImageView;

public class CarAI extends BaisAIimp {
  private Brain brain = new Brain();  

  public CarAI(TransportRepository repository) {
    super(repository);
    System.out.println("CarAI создан");
  }

  @Override
  public void run() {
    Logger.Thead(this.getClass().getSimpleName() + " итерация");
    super.run();
  }

  @Override
  public boolean isMyType(Transport t) {
    return t instanceof Car;
  }

  @Override
  public void move(Transport t) {
    ImageView iv = ((Car) t).getImageView();
    if (iv == null) return;

    // 1. ИСПРАВЛЕНО: Меняем && на || для надежного срабатывания "мозгов" в многопоточности
    if (t.getendX() == 10000 || t.getendY() == 10000) {
      double windowWidth = (iv.getScene() != null) ? iv.getScene().getWidth() : 1920;
      double windowHeight = (iv.getScene() != null) ? iv.getScene().getHeight() : 1080;
      brain.calkul(t, windowWidth, windowHeight);
    }

    Logger.carAI("move для " + t.getType() + ", id=" + t.getid());
    Logger.Carx1("сначала координаты Car: id=" + t.getid() + ", x=" + t.getx() + ", y=" + t.gety());
    Logger.carAI("dx=" + t.getdX() + ", dy=" + t.getdY());

    // 2. Рассчитываем новые координаты в модели
    double nextX = t.getx() + t.getdX();
    double nextY = t.gety() + t.getdY();
    
    t.setX(nextX);
    t.setY(nextY);
    
    Logger.Carsets("новые координаты Car: id=" + t.getid() + ", x=" + t.getx() + ", y=" + t.gety());

    // 3. ИСПРАВЛЕНО: Обновляем положение картинки строго внутри потока JavaFX
    final double finalX = nextX;
    final double finalY = nextY;
    javafx.application.Platform.runLater(() -> {
      iv.setX(finalX);
      iv.setY(finalY);
    });

    // 4. ИСПРАВЛЕНО: Безопасная проверка достижения цели (как вы сделали в TruckAI)
    // Так как dX и dY дробные, проверяем, стал ли шаг до цели меньше, чем шаг самого движения
    boolean reachedX = Math.abs(t.getx() - t.getendX()) < Math.abs(t.getdX());
    boolean reachedY = Math.abs(t.gety() - t.getendY()) < Math.abs(t.getdY());
    
    if (reachedX && reachedY) {
      t.setX(t.getendX());
      t.setY(t.getendY());
      t.setdX(0);
      t.setdY(0);
      Logger.carAI("Car ID: " + t.getid() + " достиг правого нижнего угла и завершился");
    }
  }
}