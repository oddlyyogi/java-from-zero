package shelf;

import java.util.List;

public interface Repository<ID, T extends Identified<ID>> {
    void save(T item);

    T findById(ID id);

    List<T> findAll();

    default int count() {
        return findAll().size();
    }
}
