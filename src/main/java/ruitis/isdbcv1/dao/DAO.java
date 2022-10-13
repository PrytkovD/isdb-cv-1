package ruitis.isdbcv1.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, Id> {
    void save(Entity entity);

    List<Entity> findAll();

    Optional<Entity> findById(Id id);
}
