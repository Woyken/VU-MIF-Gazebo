package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.error.Error;

public interface IErrorRepository {

    <S extends Error> S save(S entity);
}
