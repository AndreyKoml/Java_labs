package model;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Truck extends Transport {
  private ImageView imageView;
  TranslateTransition transition;

  public Truck(double x, double y,  long birthtime, long lifetime, int id) {
    super(x, y, birthtime,lifetime, id);

    Image truck = new Image("file:images/reack.png");
    imageView = new ImageView(truck);
    imageView.setX(x);
    imageView.setY(y);
    imageView.setFitWidth(60);
    imageView.setFitHeight(30);
    imageView.setPreserveRatio(true);
    }

  @Override
  public String getType() {
    return TransportType.TRUCK;
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
