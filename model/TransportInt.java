package model;
import javafx.scene.canvas.GraphicsContext;

public interface TransportInt {
    int getx();
    int gety();
    long getlifetime();
    long getbirthtime();
    int getid();
    void draw(GraphicsContext gc);
    String getType();
    
}
