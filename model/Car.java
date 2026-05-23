package model;



import javafx.animation.TranslateTransition;
// package cartrucksimulation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.Logger;


public class Car extends Transport {
  private ImageView imageView;
  TranslateTransition transition;

  public Car(double x, double y, long birthtime, long lifetime, int id) {
    super(x, y, birthtime,lifetime, id);
    Image car = new Image("file:images/car.png");
    imageView = new ImageView(car);
    imageView.setLayoutX(x);
    imageView.setLayoutY(y);
    imageView.setFitWidth(60);
    imageView.setFitHeight(30);
    imageView.setPreserveRatio(true);
    Logger.CarConstr("Car: imageView.setX(\" + x + \"), setY(\" + y + \")");
  }

  @Override
  public String getType() {
    return TransportType.CAR;
  }
@Override
  public ImageView getImageView() {
    return imageView;
  }
  public void setImageView(ImageView iv) { this.imageView = iv; }
  
  public void clearImageView() {
    this.imageView = null;
}
}
