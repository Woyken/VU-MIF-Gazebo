package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.order.Rating;

import java.util.List;

public interface IRatingRepository {

    <S extends Rating> List<S> saveAll(Iterable<S> entities);
}