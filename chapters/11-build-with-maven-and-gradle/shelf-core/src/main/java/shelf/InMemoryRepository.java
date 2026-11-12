package shelf;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<ID, T extends Identified<ID>> implements Repository<ID, T> {
    private final Map<ID, T> items = new LinkedHashMap<>();

    @Override
    public void save(T item) {
        items.put(item.id(), item);
    }

    @Override
    public T findById(ID id) {
        return items.get(id);
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(items.values());
    }
}
