package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import model.Transport;
import repository.TransportRepository;

public class HabitatView extends Pane{
    private final Canvas canvas;
    private TransportRepository repository;
    private final GraphicsContext gc;

    public HabitatView(TransportRepository repository){
        this.repository=repository;
    canvas = new Canvas(800,600);
    gc = canvas.getGraphicsContext2D();
    this.getChildren().add(canvas);
    }
    public void draw(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        for(Transport t: repository.getAll()){
            t.draw(gc);
        }
    }
    public void setRepository(TransportRepository repository){
        this.repository=repository;
    }
    
}
