import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import gui.ControlPanel;
import gui.HabitatView;
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
        simulationService.setView(habitatView);
        habitatView.setSimulationService(simulationService);
        
        // Создаём ControlPanel
        controlPanel = new ControlPanel();
        
        // Устанавливаем действия для кнопок
        controlPanel.setOnStartAction(this::onStart);
        controlPanel.setOnStopAction(this::onStop);
        
        // Устанавливаем слушатели для радиокнопок времени
        controlPanel.setOnShowTimeListener(() -> {
            simulationService.setShowTime(true);
            habitatView.setShowTime(true);
        });
        controlPanel.setOnHideTimeListener(() -> {
            simulationService.setShowTime(false);
            habitatView.setShowTime(false);
        });
        
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
                    habitatView.setShowTime(newState);
                }
                default -> {}
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void onStart() {
        System.out.println("onStart() вызван");
        try {
            int nCar = Integer.parseInt(controlPanel.getTxtCarPeriod().getText());
            int nTruck = Integer.parseInt(controlPanel.getTxtTruckPeriod().getText());
            if (nCar <= 0 || nTruck <= 0) throw new NumberFormatException();
            
            double pCar = controlPanel.getCbCarProb().getValue() / 100.0;
            double pTruck = controlPanel.getLvTruckProb().getSelectionModel().getSelectedItem() / 100.0;
        long carlifetime = Long.parseLong(controlPanel.getTxtCarLifetime().getText());
        long trucklifetime = Long.parseLong(controlPanel.getTxtTruckLifetime().getText());
            simulationService.start(nCar, nTruck, pCar, pTruck,carlifetime,trucklifetime);
            
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
        
        // Подсчёт машин
        int carCount = 0, truckCount = 0;
        for (model.Transport t : simulationService.getAll()) {
            if (t instanceof model.Car) carCount++;
            else if (t instanceof model.Truck) truckCount++;
        }
        
        long simTime = simulationService.getCurrentTime();
        
        if (controlPanel.getChkShowInfo().isSelected()) {
            showResultDialog(carCount, truckCount, simTime);
        }
    }
    
    private void showResultDialog(int carCount, int truckCount, long simTime) {
        javafx.scene.control.Dialog<String> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Результаты симуляции");
        dialog.setHeaderText("Статистика");
        
        javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea();
        textArea.setEditable(false);
        textArea.setText(
            "Время симуляции: " + simTime + " сек\n" +
            "Легковых машин: " + carCount + "\n" +
            "Грузовых машин: " + truckCount + "\n" +
            "Всего машин: " + (carCount + truckCount)
        );
        
        dialog.getDialogPane().setContent(textArea);
        
        javafx.scene.control.ButtonType okButton = new javafx.scene.control.ButtonType("ОК", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        javafx.scene.control.ButtonType cancelButton = new javafx.scene.control.ButtonType("Отмена", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        
        dialog.setResultConverter(button -> {
            if (button == okButton) return "ok";
            return null;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (!result.equals("ok")) {
                simulationService.resume();
                controlPanel.getBtnStart().setDisable(true);
                controlPanel.getBtnStop().setDisable(false);
                habitatView.refresh();
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}