package model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Truck extends Transport{
        private static final Image TRUCK_IMAGE;
            static {
        TRUCK_IMAGE = new Image("file:images/track.jpeg");
    }
    
     public Truck(int x, int y,long lifetime,long birthtime, int id){super(x,y,lifetime,birthtime,id);
                }
                
                @Override
                public void draw(GraphicsContext gc) {
    
    gc.drawImage(TRUCK_IMAGE, x, y, 80, 40);
}
                
                 @Override
                    public String getType(){
                    
                    return "Грузовая";
                }
        }

