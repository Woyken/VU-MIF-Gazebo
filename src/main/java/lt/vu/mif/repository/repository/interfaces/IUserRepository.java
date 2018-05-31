package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.user.User;
import lt.vu.mif.model.user.UserTokenTuple;

public interface IUserRepository {

    <S extends User> S save(S entity);

    void update(User user);

    void updateEmail(String currentEmail, String newEmail);

    List<User> findAll();

    void changeUserPassword(String userEmail, String password);

    void blockUser(Long userId, boolean block);

    boolean checkIfUserExists(String email);

    User getUserByEmail(String email);

    UserTokenTuple getTokenCreationDate(String token);

    User getUserByToken(String token);

    User get(Long id);

    <S extends User> List<S> saveAll(Iterable<S> entities);
}
