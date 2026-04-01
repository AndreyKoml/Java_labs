package repository;
import model.Transport;
import java.util.List;

public interface TransportRepository {
    void add(Transport transport);
    List<Transport> getAll();
    void clean();
    int count();
}
