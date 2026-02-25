package cartrucksimulation;

import javax.swing.*;
import java.awt.*;

public class Habitat extends JPanel {
    private boolean showTime = true; 
    private long lastTimeValue = 0;  

    public Habitat() {
        
        setBackground(Color.WHITE);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Очистка фона

        for (Transport v : TransportCollection.getInstance().getTransports()) {
            if (v != null) {
                v.draw(g);
            }
        }

        if (showTime && lastTimeValue > 0) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Время симуляции: " + lastTimeValue + " сек.", 15, 25);
        }
    }

    public void updateTime(long seconds) {
        this.lastTimeValue = seconds;
        repaint(); // Перерисовываем панель
    }

    public void setShowTime(boolean show) {
        this.showTime = show;
        repaint();
    }
}