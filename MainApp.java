import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class MainApp extends Application {
    
    private HabitatFX habitat;               // холст + логика генерации
private Button btnStart, btnStop;        // кнопки
private TextField txtCarPeriod, txtTruckPeriod;  // поля ввода периодов
private ComboBox<Integer> cbCarProb;     // выпадающий список вероятностей
private ListView<Integer> lvTruckProb;   // список вероятностей
private CheckBox chkShowInfo;            // флаг показа диалога
private RadioButton rbShowTime, rbHideTime;  // переключатели времени
    
    @Override
    public void start(Stage primaryStage) {
        habitat = new HabitatFX();
        
        // ================= ПАНЕЛЬ УПРАВЛЕНИЯ (справа) =================
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(15));
        controlPanel.setStyle("-fx-border-color: red; -fx-border-width: 0 0 0 1;");
        
        // --- Текстовые поля для периодов ---
        Label lblCarPeriod = new Label("Период легковых (сек):");
        txtCarPeriod = new TextField("1");
        
        Label lblTruckPeriod = new Label("Период грузовых (сек):");
        txtTruckPeriod = new TextField("2");
        
        // --- ComboBox для вероятности легковых ---
        Label lblCarProb = new Label("Вероятность легковых (%):");
        cbCarProb = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            cbCarProb.getItems().add(i * 10);
        }
        cbCarProb.setValue(50);
        
        // --- ListView для вероятности грузовых ---
        Label lblTruckProb = new Label("Вероятность грузовых (%):");
        lvTruckProb = new ListView<>();
        for (int i = 0; i <= 10; i++) {
            lvTruckProb.getItems().add(i * 10);
        }
        lvTruckProb.getSelectionModel().select(5);
        lvTruckProb.setPrefHeight(100);
        
        // --- Чекбокс "Показывать информацию" ---
        chkShowInfo = new CheckBox("Показывать информацию");
        chkShowInfo.setSelected(true);
        
        // --- Группа переключателей для времени ---
        ToggleGroup timeGroup = new ToggleGroup();
        rbShowTime = new RadioButton("Показывать время");
        rbHideTime = new RadioButton("Скрывать время");
        rbShowTime.setToggleGroup(timeGroup);
        rbHideTime.setToggleGroup(timeGroup);
        rbShowTime.setSelected(true);
        
        // --- Кнопки Старт/Стоп ---
        btnStart = new Button("Старт");
        btnStop = new Button("Стоп");
        btnStop.setDisable(true);
        
        btnStart.setOnAction(e -> onStart());
        btnStop.setOnAction(e -> onStop());
        
        // --- Обработка переключателей времени ---
        rbShowTime.setOnAction(e -> habitat.setShowTime(true));
        rbHideTime.setOnAction(e -> habitat.setShowTime(false));
        
        // --- Сборка панели управления ---
        controlPanel.getChildren().addAll(
            lblCarPeriod, txtCarPeriod,
            lblTruckPeriod, txtTruckPeriod,
            new Separator(),
            lblCarProb, cbCarProb,
            lblTruckProb, lvTruckProb,
            new Separator(),
            chkShowInfo,
            new Separator(),
            rbShowTime, rbHideTime,
            new Separator(),
            btnStart, btnStop
        );
        
        // ================= МЕНЮ =================
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Файл");
        MenuItem startItem = new MenuItem("Старт");
        MenuItem stopItem = new MenuItem("Стоп");
        MenuItem exitItem = new MenuItem("Выход");
        fileMenu.getItems().addAll(startItem, stopItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);
        
        startItem.setOnAction(e -> onStart());
        stopItem.setOnAction(e -> onStop());
        exitItem.setOnAction(e -> Platform.exit());
        
        // ================= ПАНЕЛЬ ИНСТРУМЕНТОВ =================
        ToolBar toolBar = new ToolBar();
        Button tbStart = new Button("Старт");
        Button tbStop = new Button("Стоп");
        
        tbStart.setOnAction(e -> onStart());
        tbStop.setOnAction(e -> onStop());
        
        // ================= КОРНЕВОЙ КОНТЕЙНЕР =================
        VBox topArea = new VBox();
        topArea.getChildren().addAll(menuBar, toolBar);
        
        BorderPane root = new BorderPane();
        root.setTop(topArea);
        root.setCenter(habitat);
        root.setRight(controlPanel);
        
        // ================= СЦЕНА И ОКНО =================
        Scene scene = new Scene(root, 1000, 700);
        
        // ================= КЛАВИШИ =================
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case B:
                    if (!btnStart.isDisable()) onStart();
                    break;
                case E:
                    if (!btnStop.isDisable()) onStop();
                    break;
                case T:
                    boolean newState = !habitat.isShowTime();
                    habitat.setShowTime(newState);
                    if (newState) rbShowTime.setSelected(true);
                    else rbHideTime.setSelected(true);
                    break;
                default:
                    break;
            }
        });
        
        primaryStage.setTitle("Симулятор автомобильного рынка");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        habitat.requestFocus();
    }
    
    // ================= МЕТОДЫ УПРАВЛЕНИЯ =================
    
    private void onStart() {
        try {
            // Чтение и проверка периодов
            int nCar = Integer.parseInt(txtCarPeriod.getText());
            int nTruck = Integer.parseInt(txtTruckPeriod.getText());
            if (nCar <= 0 || nTruck <= 0) throw new NumberFormatException();
            
            // Чтение вероятностей
            double pCar = cbCarProb.getValue() / 100.0;
            double pTruck = lvTruckProb.getSelectionModel().getSelectedItem() / 100.0;
            
            habitat.startSimulation(nCar, nTruck, pCar, pTruck);
            
            btnStart.setDisable(true);
            btnStop.setDisable(false);
            habitat.requestFocus();
            
        } catch (NumberFormatException e) {
            // Ошибка ввода — выставляем значения по умолчанию
            txtCarPeriod.setText("1");
            txtTruckPeriod.setText("2");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Неверное значение");
            alert.setContentText("Периоды должны быть положительными числами. Установлены значения по умолчанию (1 и 2).");
            alert.showAndWait();
        }
    }
    
    private void onStop() {
        habitat.stopSimulation();
        btnStart.setDisable(false);
        btnStop.setDisable(true);
        
        // Подсчёт машин
        int carCount = 0;
        int truckCount = 0;
        for (Transport t : TransportCollection.getInstance().getTransports()) {
            if (t instanceof Car) carCount++;
            else if (t instanceof Truck) truckCount++;
        }
        
        // Показываем диалог, если разрешено
        if (chkShowInfo.isSelected()) {
            showResultDialog(carCount, truckCount);
        } else {
            // Без диалога — просто остановка
        }
    }
    
    private void showResultDialog(int carCount, int truckCount) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Результаты симуляции");
        dialog.setHeaderText("Статистика");
        
        // TextArea для вывода информации
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText(
            "Время симуляции: " + habitat.getCurrentSimTime() + " сек\n" +
            "Легковых машин: " + carCount + "\n" +
            "Грузовых машин: " + truckCount + "\n" +
            "Всего машин: " + (carCount + truckCount)
        );
        textArea.setPrefHeight(150);
        
        dialog.getDialogPane().setContent(textArea);
        
        // Кнопки ОК и Отмена
        ButtonType okButton = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        
        // Обработка результата
        dialog.setResultConverter(button -> {
            if (button == okButton) {
                return "ok";
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(result -> {
            if (result.equals("ok")) {
                // ОК — симуляция уже остановлена, ничего не делаем
            } else {
                // Отмена — возобновляем симуляцию
                habitat.resumeSimulation();
                btnStart.setDisable(true);
                btnStop.setDisable(false);
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}