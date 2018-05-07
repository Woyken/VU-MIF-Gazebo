package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import lt.vu.mif.model.product.Image;
import lt.vu.mif.repository.repository.interfaces.IImageRepository;

@Repository
public class ImageRepository extends SimpleJpaRepository<Image, Long> implements IImageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ImageRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Image.class, entityManager),
            entityManager);
    }

    public Image get(Long id) {
        return entityManager.find(Image.class, id);
    }
}