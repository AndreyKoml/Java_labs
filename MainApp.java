import gui.ControlPanel;
import gui.HabitatView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.MemoryTransportRepository;
import repository.TransportRepository;
import service.SimulationService;
import service.SimulationServiceImpl;
public class MainApp extends Application {
    private HabitatView habitatView;
    private ControlPanel controlPanel;
    private SimulationService simulationService;
    
    @Override
    public void start(Stage primaryStage) {
        // Создаём зависимости
        TransportRepository repository = new MemoryTransportRepository();
        simulationService = new SimulationServiceImpl(repository);
        
        // Создаём View
        habitatView = new HabitatView(repository);
        
        // Создаём ControlPanel
        controlPanel = new ControlPanel();c
        
        // Привязываем действия
        controlPanel.getBtnStart().setOnAction(e -> onStart());
        controlPanel.getBtnStop().setOnAction(e -> onStop());
        controlPanel.getRbShowTime().setOnAction(e -> simulationService.setShowTime(true));
        controlPanel.getRbHideTime().setOnAction(e -> simulationService.setShowTime(false));
        
        // Компоновка
        BorderPane root = new BorderPane();
        root.setCenter(habitatView);
        root.setRight(controlPanel);
        
        // Клавиши
        Scene scene = new Scene(root, 1000, 600);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case B -> onStart();
                case E -> onStop();
                case T -> {
                    boolean newState = !simulationService.isShowTime();
                    simulationService.setShowTime(newState);
                    if (newState) controlPanel.getRbShowTime().setSelected(true);
                    else controlPanel.getRbHideTime().setSelected(true);
                }
                default -> {}
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void onStart() {
        try {
            int nCar = Integer.parseInt(controlPanel.getTxtCarPeriod().getText());
            int nTruck = Integer.parseInt(controlPanel.getTxtTruckPeriod().getText());
            if (nCar <= 0 || nTruck <= 0) throw new NumberFormatException();
            
            double pCar = controlPanel.getCbCarProb().getValue() / 100.0;
            double pTruck = controlPanel.getLvTruckProb().getSelectionModel().getSelectedItem() / 100.0;
            
            simulationService.start(nCar, nTruck, pCar, pTruck);
            
            controlPanel.getBtnStart().setDisable(true);
            controlPanel.getBtnStop().setDisable(false);
            
        } catch (NumberFormatException e) {
            controlPanel.getTxtCarPeriod().setText("1");
            controlPanel.getTxtTruckPeriod().setText("2");
            new Alert(Alert.AlertType.ERROR, "Периоды должны быть положительными числами.").showAndWait();
        }
    }
    
    private void onStop() {
        simulationService.stop();
        controlPanel.getBtnStart().setDisable(false);
        controlPanel.getBtnStop().setDisable(true);
        
        // показать статистику...
    }
}