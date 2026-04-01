package model;
import javafx.scene.canvas.GraphicsContext;

public interface TransportInt {
    int getx();
    int gety();
    void draw(GraphicsContext gc);
    String getType();
    
}
