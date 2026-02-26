import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationApplet extends JApplet {
    private Habitat habitat;
    private JButton btnStart, btnStop;
    private boolean showTime = true;
    
    @Override
    public void init() {
        setLayout(new BorderLayout());
        
        habitat = new Habitat();
        
        // Простая панель управления
        JPanel topPanel = new JPanel();
        btnStart = new JButton("Старт (B)");
        btnStop = new JButton("Стоп (E)");
        btnStop.setEnabled(false);
        
        JCheckBox chkTime = new JCheckBox("Показать время", true);
        
        btnStart.addActionListener(e -> onStart());
        btnStop.addActionListener(e -> onStop());
        chkTime.addActionListener(e -> habitat.setShowTime(chkTime.isSelected()));
        
        topPanel.add(btnStart);
        topPanel.add(btnStop);
        topPanel.add(chkTime);
        
        add(topPanel, BorderLayout.NORTH);
        add(habitat, BorderLayout.CENTER);
        
        habitat.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) onStart();
                if (e.getKeyCode() == KeyEvent.VK_E) onStop();
            }
        });
        
        habitat.requestFocusInWindow();
    }
    
    private void onStart() {
        habitat.startSimulation(1, 2, 0.5, 0.3);
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }
    
    private void onStop() {
        habitat.stopSimulation();
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        
        // Показать статистику
        int carCount = 0, truckCount = 0;
        for (Transport t : TransportCollection.getInstance().getTransports()) {
            if (t instanceof Car) carCount++;
            else truckCount++;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Время: " + habitat.getCurrentSimTime() + " сек\n" +
            "Легковых: " + carCount + "\n" +
            "Грузовых: " + truckCount,
            "Статистика", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Симулятор");
        SimulationApplet applet = new SimulationApplet();
        applet.init();
        frame.add(applet);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        applet.requestFocusInWindow();
    }
}