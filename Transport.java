import java.awt.Graphics;

public abstract class Transport implements IBehaviour {
    protected int x, y;
    
    public Transport(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void draw(Graphics g);
    public abstract String getType();
    
    @Override
    public void move() {
    }
}