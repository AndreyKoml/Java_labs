package gui;





import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    
    private final TextField txtCarPeriod;
    private final TextField txtTruckPeriod;
    private final ComboBox<Integer> cbCarProb;
    private final ListView<Integer> lvTruckProb;
    private final CheckBox chkShowInfo;
    private final RadioButton rbShowTime;
    private final RadioButton rbHideTime;
    private final Button btnStart;
    private final Button btnStop;
    
    public ControlPanel() {
        super(10);
        setPadding(new Insets(15));
        setAlignment(Pos.TOP_LEFT);
        
        // Периоды
        Label lblCarPeriod = new Label("Период легковых (сек):");
        txtCarPeriod = new TextField("1");
        
        Label lblTruckPeriod = new Label("Период грузовых (сек):");
        txtTruckPeriod = new TextField("2");
        
        // Вероятности
        Label lblCarProb = new Label("Вероятность легковых (%):");
        cbCarProb = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            cbCarProb.getItems().add(i * 10);
        }
        cbCarProb.setValue(50);
        
        Label lblTruckProb = new Label("Вероятность грузовых (%):");
        lvTruckProb = new ListView<>();
        for (int i = 0; i <= 10; i++) {
            lvTruckProb.getItems().add(i * 10);
        }
        lvTruckProb.getSelectionModel().select(5);
        lvTruckProb.setPrefHeight(100);
        
        // Чекбокс
        chkShowInfo = new CheckBox("Показывать информацию");
        chkShowInfo.setSelected(true);
        
        // Радиокнопки времени
        ToggleGroup timeGroup = new ToggleGroup();
        rbShowTime = new RadioButton("Показывать время");
        rbHideTime = new RadioButton("Скрывать время");
        rbShowTime.setToggleGroup(timeGroup);
        rbHideTime.setToggleGroup(timeGroup);
        rbShowTime.setSelected(true);
        
        // Кнопки
        btnStart = new Button("Старт");
        btnStop = new Button("Стоп");
        btnStop.setDisable(true);
        
        // Сборка
        getChildren().addAll(
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
    }
    
    // Геттеры для доступа из MainApp
    public TextField getTxtCarPeriod() { return txtCarPeriod; }
    public TextField getTxtTruckPeriod() { return txtTruckPeriod; }
    public ComboBox<Integer> getCbCarProb() { return cbCarProb; }
    public ListView<Integer> getLvTruckProb() { return lvTruckProb; }
    public CheckBox getChkShowInfo() { return chkShowInfo; }
    public RadioButton getRbShowTime() { return rbShowTime; }
    public RadioButton getRbHideTime() { return rbHideTime; }
    public Button getBtnStart() { return btnStart; }
    public Button getBtnStop() { return btnStop; }
    }

