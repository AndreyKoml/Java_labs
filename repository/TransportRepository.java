package repository;

import java.util.List;
import model.Transport;

public interface TransportRepository {
  void add(Transport transport);

  void addid(int id);

  void remove(Transport transport);

  void removeid(int id);

  List<Transport> getAll();

  void clean();

  int count();
}
