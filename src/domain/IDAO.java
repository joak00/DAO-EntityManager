package core;

import java.util.UUID;
import java.util.Optional;

public interface IDAO<T> {
    void insert(T t);
    void update(T t);
    void delete(T t);
    Optional<T> select(UUID id);

}
