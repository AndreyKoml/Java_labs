package alfragment;

import model.Transport;
import model.TransportType;
import java.util.Random;
import util.Logger;

class Brain {
  
  // Добавляем параметры width и height, чтобы знать реальные размеры экрана
  public void calkul(Transport t, double windowWidth, double windowHeight) {
    Logger.brain("Brain.calkul для " + t.getType() + ", id=" + t.getid() + ", endX=" + t.getendX() + ", endY=" + t.getendY());
    
    // Если цель еще не задана (стоит начальный флаг 10000)
    if (t.getendX() == 10000 || t.getendY() == 10000) {
      Random random = new Random();
      
      if (t.getType() == TransportType.CAR) {
        // ЛЕГКОВЫЕ: Должны ехать в ПРАВЫЙ НИЖНИЙ угол
        // Выбираем случайную точку в правой нижней четверти экрана
        double targetX = random.nextDouble(windowWidth * 0.7, windowWidth - 80);
        double targetY = random.nextDouble(windowHeight * 0.7, windowHeight - 60);
        
        t.setendX(targetX);
        t.setendY(targetY);
        
      } else {
        // ГРУЗОВИКИ: Должны ехать в ЛЕВЫЙ ВЕРХНИЙ угол
        // Выбираем случайную точку в левой верхней четверти экрана (например, от 0 до 300 пикселей)
        double targetX = random.nextDouble(0, Math.min(300, windowWidth * 0.3));
        double targetY = random.nextDouble(0, Math.min(300, windowHeight * 0.3));
        
        t.setendX(targetX);
        t.setendY(targetY);
        
        Logger.TruckBrain("endTruck: " + t.getid() + " targetX: " + t.getendX() + " targetY: " + t.getendY());
      }
      
      // РАСЧЕТ ВЕКТОРОВ СКОРОСТИ (dX и dY)
      double deltaX = t.getendX() - t.getx();   
      double deltaY = t.getendY() - t.gety();
      double steps = Math.max(Math.abs(deltaX), Math.abs(deltaY));
      
      if (steps == 0) {
        t.setdX(0);
        t.setdY(0);
      } else {
        // Задаем шаг движения
        t.setdX(deltaX / steps);
        t.setdY(deltaY / steps);
        
        if (t.getType() == TransportType.TRUCK) {
          Logger.TruckD("TruckDx: " + t.getdX() + " / TruckDy: " + t.getdY());
        }
      }
    }
  }
}