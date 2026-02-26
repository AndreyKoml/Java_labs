import java.awt.Color;
import java.awt.Graphics;

public class Car extends Transport {
    
    public Car(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRoundRect(x, y, 60, 30, 10, 10);
        g.setColor(Color.black);
        g.fillOval(x + 10, y + 25, 10, 10);
        g.fillOval(x + 40, y + 25, 10, 10);
        g.setColor(Color.green.darker());
        g.drawString("Car", x + 15, y + 20);
    }
    
    @Override
    public String getType() {
        return "Легковая";
    }
}