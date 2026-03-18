package cartrucksimulation;

import java.awt.Graphics;

abstract class Transport implements IBehaviour {
            
            protected int x,y;
            protected int id;             
            protected long birthTime;     
            protected long lifeTime; 
            
            public Transport(int x, int y){
                this.x = x;
                this.y = y;
            }
            public abstract void draw(Graphics g);
            public abstract String getType();
            
            public int getId() { 
                return id; 
            }
            public void setId(int id) { 
                this.id = id; 
            }

            public long getBirthTime() { 
                return birthTime; 
            }
            public void setBirthTime(long birthTime) { 
                this.birthTime = birthTime; 
            }

            public long getLifeTime() { 
                return lifeTime; 
            }
            public void setLifeTime(long lifeTime) { 
                this.lifeTime = lifeTime; 
            }

            @Override
            public void move(){
            }
}
