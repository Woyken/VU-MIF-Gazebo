package lt.vu.mif.ui.helpers.interfaces;

import lt.vu.mif.model.user.UserTokenTuple;
import lt.vu.mif.ui.view.UserView;
import java.util.List;

public interface IUserHelper {
    void updateUserEmail(String userEmail, String newEmail);

    void changeCurrentUserPassword(String newPassword);

    boolean checkIfUserExists(String userEmail);

    void updateUserToken(String userEmail);

    UserTokenTuple getTokenCreationDate(String token);

    void changeUserPasswordAndDeleteToken(String userEmail, String password);

    void saveNewUser(String password, String email);

    List<UserView> getAllUsers();
    
    void blockUser(Long userId, boolean isBlocked);

    UserView get(Long id);
}
