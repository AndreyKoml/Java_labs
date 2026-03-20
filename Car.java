//package cartrucksimulation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Car extends Transport{

            public Car(int x, int y/* long birthTime, long lifeTime*/ ){super(x,y);
            
                this.birthTime = birthTime;
                this.lifeTime = lifeTime;
            }
                @Override
                public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(x, y, 60, 30, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillOval(x + 10, y + 25, 10, 10);
        gc.fillOval(x + 40, y + 25, 10, 10);
    gc.setFill(Color.BLUE);
    gc.fillText("Car", x + 15, y + 20);
}
                
                @Override
                public String getType(){
                    
                return "Легковая";
                }
}

