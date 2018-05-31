package lt.vu.mif.ui.helpers.interfaces;

import java.util.List;
import lt.vu.mif.model.user.UserTokenTuple;
import lt.vu.mif.ui.view.AdminUserView;
import lt.vu.mif.ui.view.UserView;

public interface IUserHelper {

    void updateUserEmail(String userEmail, String newEmail);

    void changeCurrentUserPassword(String newPassword);

    boolean checkIfUserExists(String userEmail);

    void remindPassword(String userEmail);

    UserTokenTuple getTokenCreationDate(String token);

    void changeUserPasswordAndDeleteToken(String userEmail, String password);

    void saveNewUser(String password, String email);

    List<AdminUserView> getAllUsers();

    void blockUser(Long userId, boolean isBlocked);

    UserView get(Long id);

    AdminUserView getAdminView(Long id);

    UserView getLoggedInUser();

    boolean isLoggedIn();
}
