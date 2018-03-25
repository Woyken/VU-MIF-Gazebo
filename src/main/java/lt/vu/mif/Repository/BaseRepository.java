package lt.vu.mif.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BaseRepository<T> {

    @Autowired
    private EntityManager entityManager;

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public void save(List<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    public T get(Class<T> clazz, Long id) {
        return entityManager.find(clazz, id);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
