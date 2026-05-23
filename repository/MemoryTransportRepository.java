package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Transport;

public class MemoryTransportRepository implements TransportRepository {

  private static MemoryTransportRepository instance; // ← единственный экземпляр

  private final List<Transport> transports = new CopyOnWriteArrayList<>();
  private final TreeSet<Integer> ids = new TreeSet<>();
  private final HashMap<Integer, Long> idBirth = new HashMap<>();

  // Приватный конструктор — запрещает создание через new
  private MemoryTransportRepository() {}

  public static synchronized MemoryTransportRepository getInstance() {
    if (instance == null) {
      instance = new MemoryTransportRepository();
    }
    return instance;
  }

  @Override
  public void add(Transport transport) {
    transports.add(transport);
    ids.add(transport.getid());
    idBirth.put(transport.getid(), transport.getbirthtime());
  }

  @Override
  public void addid(int id) {
    ids.add(id);
  }

  @Override
  public void removeid(int id) {
    ids.remove(id);
  }

  @Override
  public List<Transport> getAll() {
    return new ArrayList<>(transports);
  }

  @Override
  public void clean() {
    transports.clear();
    ids.clear();
    idBirth.clear();
  }

  @Override
  public int count() {
    return transports.size();
  }

  @Override
  public void remove(Transport transport) {
    transports.remove(transport);
    ids.remove(transport.getid());
    idBirth.remove(transport.getid());
  }
}
