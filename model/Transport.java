package model;

// package cartrucksimulation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public abstract class Transport implements TransportInt {
  protected double dx, dy;
  protected int id;
  protected double x,y;
  protected double endX= 10000;
  protected double endY = 10000;
  protected long lifetime, birthtime;
  protected boolean readyToMove = false;

  public Transport(
    double x,
    double y,
    long birthtime,
    long lifetime,
    int id
    ){
    this.x = x;
    this.y = y;
    this.lifetime = lifetime;
    this.birthtime = birthtime;
    this.id = id;
  }

  public abstract String getType();

  @Override
  public double getx() {
    return x;
  }

  @Override
  public double gety() {
    return y;
  }

  @Override
  public long getlifetime() {
    return lifetime;
  }

  @Override
  public long getbirthtime() {
    return birthtime;
  }

  @Override
  public int getid() {
    return id;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }
  public void setendX(double x) {
    this.endX = x;
  }

  public void setendY(double y) {
    this.endY = y;
  }
  public void setdX(double x){
    this.dx = x;
  }
    public void setdY(double y){
    this.dy = y;
  }

  public double getendX(){
    return endX;
  }
  public double getendY(){
    return endY;
  }
  public double getdX(){
    return dx;
  }
  public double getdY(){
    return dy;
  }
  public abstract ImageView getImageView();

  public boolean getreadyToMove(){return readyToMove;}
  public void setreadyToMove(boolean readyToMove){this.readyToMove = readyToMove; }
  
}
