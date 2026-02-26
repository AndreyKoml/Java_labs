package cartrucksimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationApplet extends JApplet {
    private Habitat habitat;
    private JButton btnStart, btnStop;
    private JTextField txtCarP, txtTruckP;
    private JComboBox comboCar;
    private JList listTruck;
    private JCheckBox chkInfo;
    private JRadioButton rbOn, rbOff;

    @Override
    public void init() {
        setSize(1000, 600);
        habitat = new Habitat();
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("Машинки");
        JMenuItem miS = new JMenuItem("Старт (B)");
        JMenuItem miE = new JMenuItem("Стоп (E)");
        m.add(miS); m.add(miE); bar.add(m); setJMenuBar(bar);

        JToolBar tb = new JToolBar();
        JButton tS = new JButton("Пуск"); JButton tE = new JButton("Стоп");
        tb.add(tS); tb.add(tE); add(tb, BorderLayout.NORTH);

        JPanel pRight = new JPanel();
        pRight.setLayout(new BoxLayout(pRight, BoxLayout.Y_AXIS));
        pRight.setPreferredSize(new Dimension(250, 0));
        pRight.setBorder(BorderFactory.createTitledBorder("Настройки"));

        txtCarP = new JTextField("1"); txtTruckP = new JTextField("2");
        String[] p = {"0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
        comboCar = new JComboBox(p); comboCar.setSelectedIndex(5);
        listTruck = new JList(p); listTruck.setSelectedIndex(5); listTruck.setVisibleRowCount(4);

        chkInfo = new JCheckBox("Окно инфо при остановке", true);
        rbOn = new JRadioButton("Показать время", true); rbOff = new JRadioButton("Скрыть время");
        ButtonGroup bg = new ButtonGroup(); bg.add(rbOn); bg.add(rbOff);

        btnStart = new JButton("Старт"); btnStop = new JButton("Стоп");
        btnStop.setEnabled(false);

        pRight.add(new JLabel("Период Легковой:")); pRight.add(txtCarP);
        pRight.add(new JLabel("Вероятность Легковой:")); pRight.add(comboCar);
        pRight.add(new JLabel("Период Грузовой:")); pRight.add(txtTruckP);
        pRight.add(new JLabel("Вероятность Грузовой:")); pRight.add(new JScrollPane(listTruck));
        pRight.add(rbOn); pRight.add(rbOff); pRight.add(chkInfo);
        pRight.add(Box.createVerticalGlue()); pRight.add(btnStart); pRight.add(btnStop);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, habitat, pRight);
        split.setDividerLocation(700);
        add(split, BorderLayout.CENTER);

        
        ActionListener startAct = e -> onStart();
        ActionListener stopAct = e -> onStop();

        btnStart.addActionListener(startAct); miS.addActionListener(startAct); tS.addActionListener(startAct);
        btnStop.addActionListener(stopAct); miE.addActionListener(stopAct); tE.addActionListener(stopAct);

        rbOn.addActionListener(e -> habitat.setShowTime(true));
        rbOff.addActionListener(e -> habitat.setShowTime(false));

        habitat.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) onStart();
                if (e.getKeyCode() == KeyEvent.VK_E) onStop();
            }
        });
    }

    private void onStart() {
        if (habitat.isRun()) return;
        try {
            int nc = Integer.parseInt(txtCarP.getText());
            int nt = Integer.parseInt(txtTruckP.getText());
            double pc = comboCar.getSelectedIndex() / 10.0;
            double pt = listTruck.getSelectedIndex() / 10.0;
            habitat.startSimulation(nc, nt, pc, pt);
            btnStart.setEnabled(false); btnStop.setEnabled(true);
            habitat.requestFocusInWindow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка в числах!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            txtCarP.setText("1"); txtTruckP.setText("2");
        }
    }

    private void onStop() {
        if (!habitat.isRun()) return;
        habitat.stopSimulation();
        if (chkInfo.isSelected()) {
            showResultDialog();
        } else {
            finaleStop();
        }
    }

    private void showResultDialog() {
        int c = 0, t = 0;
        for (Transport v : TransportCollection.getInstance().getTransports()) {
            if (v instanceof Car) c++; else t++;
        }
        
        JDialog d = new JDialog((Frame)null, "Статистика", true);
        d.setLayout(new BorderLayout());
        JTextArea area = new JTextArea("Время: " + habitat.getCurrentSimTime() + "\nЛегковая: " + c + "\nГрузовая: " + t);
        area.setEditable(false);
        d.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton ok = new JButton("ОК"); JButton can = new JButton("Отмена");
        ok.addActionListener(e -> { finaleStop(); d.dispose(); });
        can.addActionListener(e -> { habitat.resumeSimulation(); d.dispose(); });
        
        JPanel p = new JPanel(); p.add(ok); p.add(can);
        d.add(p, BorderLayout.SOUTH);
        d.setSize(250, 200); d.setLocationRelativeTo(this); d.setVisible(true);
    }

    private void finaleStop() {
        btnStart.setEnabled(true); btnStop.setEnabled(false);
    }
}
