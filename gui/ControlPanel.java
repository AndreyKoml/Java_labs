package gui;

import javafx.application.Platform;
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
    private Runnable onShowTimeListener;
    private Runnable onHideTimeListener;
    private final TextField txtCarLifetime;
    private final TextField txtTruckLifetime;
    private final Button btnShowObjects;
    // Ссылки на действия (устанавливаются из MainApp)
    private Runnable onStartAction;
    private Runnable onStopAction;
    private Runnable onShowObjectsAction;
    public TextField getTxtCarLifetime() { return txtCarLifetime; }
    public TextField getTxtTruckLifetime() { return txtTruckLifetime; }
    public void setOnShowObjectsAction(Runnable action) {
    this.onShowObjectsAction = action;
}
    public ControlPanel() {
        super(10);
        txtCarLifetime = new TextField("10");
    txtTruckLifetime = new TextField("15");
        setPadding(new Insets(15));
        setAlignment(Pos.TOP_LEFT);
        btnShowObjects = new Button("Текущие объекты");
btnShowObjects.setOnAction(e -> {
    if (onShowObjectsAction != null) onShowObjectsAction.run();
});
        // ================= МЕНЮ =================
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Файл");
        MenuItem startItem = new MenuItem("Старт");
        MenuItem stopItem = new MenuItem("Стоп");
        MenuItem exitItem = new MenuItem("Выход");
        fileMenu.getItems().addAll(startItem, stopItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);
        
        // Действия меню (будут привязаны позже)
        startItem.setOnAction(e -> {
            if (onStartAction != null) onStartAction.run();
        });
        stopItem.setOnAction(e -> {
            if (onStopAction != null) onStopAction.run();
        });
        exitItem.setOnAction(e -> Platform.exit());
        
        // ================= ПАНЕЛЬ ИНСТРУМЕНТОВ =================

        Button tbStart = new Button("Старт");
        Button tbStop = new Button("Стоп");
        ToolBar toolBar = new ToolBar(tbStart, tbStop);
        tbStart.setOnAction(e -> {
            if (onStartAction != null) onStartAction.run();
        });
        tbStop.setOnAction(e -> {
            if (onStopAction != null) onStopAction.run();
        });
        //toolBar.getChildren().addAll(tbStart, tbStop);
        
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
        rbShowTime.setOnAction(e -> {
    if (onShowTimeListener != null) onShowTimeListener.run();
});
rbHideTime.setOnAction(e -> {
    if (onHideTimeListener != null) onHideTimeListener.run();
});
        
        // Кнопки
        btnStart = new Button("Старт");
        btnStop = new Button("Стоп");
        btnStop.setDisable(true);
        
        btnStart.setOnAction(e -> {
            if (onStartAction != null) onStartAction.run();
        });
        btnStop.setOnAction(e -> {
            if (onStopAction != null) onStopAction.run();
        });
        
        // Сборка
        getChildren().addAll(
            menuBar, toolBar, 
            
new Label("Время жизни легковых (сек):"),
txtCarLifetime,
new Label("Время жизни грузовых (сек):"),
txtTruckLifetime,
new Separator(),
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
            btnStart, btnStop,btnShowObjects
        );
    }
    
    // Методы для установки действий из MainApp
    public void setOnStartAction(Runnable action) {
        this.onStartAction = action;
    }
    
    public void setOnStopAction(Runnable action) {
        this.onStopAction = action;
    }
    
    // Геттеры
    public TextField getTxtCarPeriod() { return txtCarPeriod; }
    public TextField getTxtTruckPeriod() { return txtTruckPeriod; }
    public ComboBox<Integer> getCbCarProb() { return cbCarProb; }
    public ListView<Integer> getLvTruckProb() { return lvTruckProb; }
    public CheckBox getChkShowInfo() { return chkShowInfo; }
    public RadioButton getRbShowTime() { return rbShowTime; }
    public RadioButton getRbHideTime() { return rbHideTime; }
    public Button getBtnStart() { return btnStart; }
    public Button getBtnStop() { return btnStop; }
    public void setOnShowTimeListener(Runnable action) {
    this.onShowTimeListener = action;
}

public void setOnHideTimeListener(Runnable action) {
    this.onHideTimeListener = action;
}
}