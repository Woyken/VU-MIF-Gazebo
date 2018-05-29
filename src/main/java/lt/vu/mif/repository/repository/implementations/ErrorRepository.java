package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import lt.vu.mif.model.error.Error;
import lt.vu.mif.repository.repository.interfaces.IErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ErrorRepository extends SimpleJpaRepository<Error, Long> implements IErrorRepository {

    @Autowired
    public ErrorRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Error.class, entityManager),
            entityManager);
    }
}