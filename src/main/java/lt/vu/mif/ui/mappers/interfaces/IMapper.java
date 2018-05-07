package lt.vu.mif.ui.mappers.interfaces;

import java.util.List;

public interface IMapper<E, V> {

    E toEntity(V view);

    List<E> toEntities(List<V> views);

    V toView(E entity);

    List<V> toViews(List<E> entities);
}
