package lt.vu.mif.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

@Repository
@Transactional
public class BaseRepository<T> {

    @Autowired
    private EntityManager entityManager;

    public void save(T entity) {
        entityManager.persist(entity);
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
