//package cartrucksimulation;

//import cartrucksimulation.HabitatFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    private HabitatFX habitat;  // используем тот же Habitat, но обновим его под JavaFX
    
    @Override
    public void start(Stage primaryStage) {
        habitat = new HabitatFX();
        
        HBox topPanel = new HBox(10);
        Button btnStart = new Button("Старт");
        Button btnStop = new Button("Стоп");
        btnStop.setDisable(true);
        
        btnStart.setOnAction(e -> {
            habitat.startSimulation(1, 2, 0.5, 0.3);
            btnStart.setDisable(true);
            btnStop.setDisable(false);
        });
        
        btnStop.setOnAction(e -> {
            habitat.stopSimulation();
            btnStart.setDisable(false);
            btnStop.setDisable(true);
        });
        
        topPanel.getChildren().addAll(btnStart, btnStop);
        
        BorderPane root = new BorderPane();
        root.setTop(topPanel);
        root.setCenter(habitat);
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Симулятор (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        habitat.requestFocus();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}