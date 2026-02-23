package cartrucksimulation;

import javax.swing.JApplet;

public class SimulationApplet extends JApplet {
    
    @Override
    public void init() {
        
        setSize(800, 600);

        Habitat habitat = new Habitat();
        add(habitat);
    }
}
