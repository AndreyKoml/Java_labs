package repository;
import model.Transport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class MemoryTransportRepository implements TransportRepository{ 
    private final List<Transport> transports = new ArrayList<>(); 
    private final TreeSet<Integer>ids=new TreeSet<>();
    private final HashMap<Integer,String>id_birh=new HashMap<>();
    @Override
    public void add(Transport transport){transports.add(transport);
                                        ids.add(transport.getid());
                                    id_birh.put(transport.getid(),Long.toString(transport.getbirthtime()));}
    

    @Override
    public void addid(int id){ids.add(id);}
    @Override
    public void removeid(int id){ids.remove(id);}
    @Override
    public List<Transport> getAll(){return new ArrayList<>(transports);}

    @Override
    public void clean(){transports.clear();}

    @Override
    public int count(){return transports.size();}
    @Override
    public void remove(Transport transport){transports.remove(transport);
                                            ids.remove(transport.getid());
                                            id_birh.remove(transport.getid(),Long.toString(transport.getbirthtime()));
    }
    
}
