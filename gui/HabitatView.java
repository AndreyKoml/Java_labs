package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Transport;
import repository.TransportRepository;
import service.SimulationService;

public class HabitatView extends Pane {
    private final Canvas canvas;
    private TransportRepository repository;
    private final GraphicsContext gc;
    private SimulationService simulationService;  // ← добавить
    private boolean showTime = true;              // ← добавить

    public HabitatView(TransportRepository repository) {
        this.repository = repository;
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }
    
    public void setSimulationService(SimulationService service) {
        this.simulationService = service;
    }
    
    public void setShowTime(boolean show) {
        this.showTime = show;
    }
    
    public void draw() {
        System.out.println("draw(), машин: " + repository.getAll().size());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        for (Transport t : repository.getAll()) {
            t.draw(gc);
        }
        
        // ← ДОБАВИТЬ ОТРИСОВКУ ВРЕМЕНИ
        if (showTime &&  simulationService.isRun()) {
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Arial", 14));
            gc.fillText("Время: " + simulationService.getCurrentTime() + " сек", 10, 20);
        }
    }
    
    public void setRepository(TransportRepository repository) {
        this.repository = repository;
    }
    
    public void update() {
        draw();
    }
    
    public void refresh() {
        draw();
    }
    
}