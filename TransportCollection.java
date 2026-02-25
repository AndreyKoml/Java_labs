package cartrucksimulation;

import java.util.ArrayList;

public class TransportCollection {
    
    private static TransportCollection instance;
    private ArrayList<Transport> transports;
    private TransportCollection(){
        transports = new ArrayList<>();
    }
    
    public static synchronized TransportCollection getInstance() {
        
        if(instance == null){
            instance = new TransportCollection();
        }
        return instance;
    }
    
    public ArrayList<Transport> getTransports(){
        return transports;
    }
    
    public void clear(){
        transports.clear();
    }
}
