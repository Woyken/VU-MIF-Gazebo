package lt.vu.mif.Utils;

import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersistenceUtils {
    private static final Log LOG = LogFactory.getLog(PersistenceUtils.class);

    public static <T> T uniqueResult(TypedQuery<T> query) throws NonUniqueResultException {
        try {
            return getUniqueResult(query.getResultList());
        } catch (NonUniqueResultException ex) {
            throw new NonUniqueResultException(ex.getMessage());
        }
    }

    private static <T> T getUniqueResult(List<T> result) throws NonUniqueResultException {
        if (result != null && !result.isEmpty()) {
            if (result.size() != 1) {
                String message = String.format("Could not fetch unique result of type %1s", result.get(0).getClass().getName());
                LOG.error(message);
                throw new NonUniqueResultException(message);
            } else {
                return result.get(0);
            }
        } else {
            return null;
        }
    }

    public static Predicate[] toArray(List<Predicate> predicates) {
        if (predicates == null) {
            throw new IllegalArgumentException("Parameter predicates is required");
        } else {
            return predicates.toArray(new Predicate[predicates.size()]);
        }
    }
}