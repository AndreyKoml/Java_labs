package cartrucksimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SimulationApplet extends JApplet {
    private Habitat habitat;
    private Timer timer;
    private long startTime;
    private long lastCarTime, lastTruckTime;
    private boolean isRun = false;

    private JButton btnStart, btnStop;
    private JCheckBox chkShowInfo;
    private JRadioButton rbShowTime, rbHideTime;
    private JTextField txtCarPeriod, txtTruckPeriod;
    private JComboBox<String> comboCarProb;
    private JList<String> listTruckProb;

    private int N_Car = 1, N_Truck = 2;
    private double P_Car = 0.5, P_Truck = 0.5;

    @Override
    public void init() {
        setSize(1000, 700);
        setLayout(new BorderLayout());

        habitat = new Habitat();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Управление");
        JMenuItem mStart = new JMenuItem("Старт (B)");
        JMenuItem mStop = new JMenuItem("Стоп (E)");
        menu.add(mStart); menu.add(mStop);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar();
        JButton tStart = new JButton("Старт");
        JButton tStop = new JButton("Стоп");
        toolBar.add(tStart); toolBar.add(tStop);
        add(toolBar, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Панель управления"));

        controlPanel.add(new JLabel("Период Легковой (сек):"));
        txtCarPeriod = new JTextField("1");
        controlPanel.add(txtCarPeriod);

        controlPanel.add(new JLabel("Период Грузовой (сек):"));
        txtTruckPeriod = new JTextField("2");
        controlPanel.add(txtTruckPeriod);

        String[] probs = {"0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};//комбобокс
        controlPanel.add(new JLabel("Вероятность Легковой:"));
        comboCarProb = new JComboBox<>(probs);
        comboCarProb.setSelectedIndex(5);
        controlPanel.add(comboCarProb);

        controlPanel.add(new JLabel("Вероятность Грузовой:"));//лист
        listTruckProb = new JList<>(probs);
        listTruckProb.setSelectedIndex(5);
        listTruckProb.setVisibleRowCount(4);
        controlPanel.add(new JScrollPane(listTruckProb));

        rbShowTime = new JRadioButton("Показать время", true);
        rbHideTime = new JRadioButton("Скрыть время");
        ButtonGroup group = new ButtonGroup();
        group.add(rbShowTime); group.add(rbHideTime);
        controlPanel.add(rbShowTime); controlPanel.add(rbHideTime);

        chkShowInfo = new JCheckBox("Показывать инфо", true);
        controlPanel.add(chkShowInfo);

        btnStart = new JButton("Старт");
        btnStop = new JButton("Стоп");
        btnStop.setEnabled(false);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(btnStart); controlPanel.add(btnStop);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, habitat, controlPanel);
        splitPane.setDividerLocation(750);
        add(splitPane, BorderLayout.CENTER);

        ActionListener startAction = e -> startSimulation();
        ActionListener stopAction = e -> requestStop();

        btnStart.addActionListener(startAction);
        tStart.addActionListener(startAction);
        mStart.addActionListener(startAction);

        btnStop.addActionListener(stopAction);
        tStop.addActionListener(stopAction);
        mStop.addActionListener(stopAction);

        rbShowTime.addActionListener(e -> habitat.setShowTime(true));
        rbHideTime.addActionListener(e -> habitat.setShowTime(false));

        timer = new Timer(100, e -> updateLogic());

        habitat.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) startSimulation();
                if (e.getKeyCode() == KeyEvent.VK_E) requestStop();
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    rbShowTime.setSelected(!rbShowTime.isSelected());
                    habitat.setShowTime(rbShowTime.isSelected());
                }
            }
        });
    }

    private void startSimulation() {
        if (isRun) return;

        try {
            N_Car = Integer.parseInt(txtCarPeriod.getText());
            N_Truck = Integer.parseInt(txtTruckPeriod.getText());
            if (N_Car <= 0 || N_Truck <= 0) throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка ввода! Установлены значения по умолчанию (1, 2).", "Ошибка", JOptionPane.ERROR_MESSAGE);
            txtCarPeriod.setText("1"); txtTruckPeriod.setText("2");
            N_Car = 1; N_Truck = 2;
        }

        P_Car = comboCarProb.getSelectedIndex() / 10.0;
        P_Truck = listTruckProb.getSelectedIndex() / 10.0;

        TransportCollection.getInstance().clear();
        startTime = System.currentTimeMillis();
        lastCarTime = 0; lastTruckTime = 0;
        isRun = true;
        
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        timer.start();
        habitat.requestFocusInWindow();
    }

    private void requestStop() {
        if (!isRun) return;
        timer.stop();
        isRun = false;

        if (chkShowInfo.isSelected()) {
            showModalDialog();
        } else {
            confirmStop();
        }
    }

    private void showModalDialog() {
        int carCount = 0, truckCount = 0;
        for (Transport v : TransportCollection.getInstance().getTransports()) {
            if (v instanceof Car) carCount++; else truckCount++;
        }

        JDialog dialog = new JDialog((Frame)null, "Результаты симуляции", true);
        dialog.setLayout(new BorderLayout());

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setText("Общее время: " + (System.currentTimeMillis() - startTime)/1000 + " сек\n" +
                     "Легковых авто: " + carCount + "\n" +
                     "Грузовых авто: " + truckCount);
        
        dialog.add(new JScrollPane(info), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnOk = new JButton("ОК");
        JButton btnCancel = new JButton("Отмена");

        btnOk.addActionListener(e -> { confirmStop(); dialog.dispose(); });
        btnCancel.addActionListener(e -> { resumeSimulation(); dialog.dispose(); });

        btnPanel.add(btnOk); btnPanel.add(btnCancel);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void confirmStop() {
        isRun = false;
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        habitat.repaint();
    }

    private void resumeSimulation() {
        isRun = true;
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        timer.start();
        habitat.requestFocusInWindow();
    }

    private void updateLogic() {
        long elapsed = (System.currentTimeMillis() - startTime);
        Random r = new Random();

        if (elapsed - lastCarTime >= N_Car * 1000) {
            lastCarTime = elapsed;
            if (r.nextDouble() < P_Car) TransportCollection.getInstance().getTransports().add(new Car(r.nextInt(habitat.getWidth()-60), r.nextInt(habitat.getHeight()-40)));
        }
        if (elapsed - lastTruckTime >= N_Truck * 1000) {
            lastTruckTime = elapsed;
            
            if (r.nextDouble() < P_Truck) TransportCollection.getInstance().getTransports().add(new Truck(r.nextInt(habitat.getWidth()-80), r.nextInt(habitat.getHeight()-40)));
        }
        
        habitat.updateTime(elapsed / 1000);
    }
}
