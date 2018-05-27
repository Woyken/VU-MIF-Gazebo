package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.order.Rating;

public interface IRatingRepository {

    <S extends Rating> List<S> saveAll(Iterable<S> entities);
}