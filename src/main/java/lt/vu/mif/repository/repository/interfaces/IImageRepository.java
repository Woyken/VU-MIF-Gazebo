package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.product.Image;

public interface IImageRepository {
    Image get(Long id);
}
