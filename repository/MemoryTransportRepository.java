package repository;
import model.Transport;
import java.util.ArrayList;
import java.util.List;

public class MemoryTransportRepository implements TransportRepository{ 
    private final List<Transport> transports = new ArrayList<>(); 
    @Override
    public void add(Transport transport){transports.add(transport);}
    
    @Override
    public List<Transport> getAll(){return new ArrayList<>(transports);}

    @Override
    public void clean(){transports.clear();}

    @Override
    public int count(){return transports.size();}
    @Override
    public void remove(Transport transport){transports.remove(transport);}
}
