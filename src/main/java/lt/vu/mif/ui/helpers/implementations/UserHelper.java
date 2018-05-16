package lt.vu.mif.ui.helpers.implementations;

import java.time.LocalDateTime;
import java.util.List;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import lt.vu.mif.email.interfaces.IEmailProvider;
import lt.vu.mif.model.user.Role;
import lt.vu.mif.model.user.User;
import lt.vu.mif.model.user.UserTokenTuple;
import lt.vu.mif.repository.repository.interfaces.IUserRepository;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import lt.vu.mif.ui.mappers.implementations.UserMapper;
import lt.vu.mif.ui.view.UserView;
import lt.vu.mif.utils.interfaces.ITokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserHelper implements IUserHelper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IEmailContentGenerator emailContentGenerator;
    @Autowired
    private IEmailProvider emailProvider;
    @Autowired
    private ITokenGenerator tokenGenerator;


    public UserView get(Long id) {
        User user = userRepository.get(id);
        return user == null ? null : userMapper.toView(user);
    }

    @Override
    public void blockUser(Long userId, boolean isBlocked) {
        userRepository.blockUser(userId, isBlocked);
    }

    @Override
    public void updateUserEmail(String userEmail, String newEmail) {
        userRepository.updateEmail(userEmail, newEmail);
    }

    public void saveNewUser(String password, String email) {
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public void changeCurrentUserPassword(String newPassword) {
        userRepository.changeUserPassword(userService.getLoggedUserEmail(), passwordEncoder.encode(newPassword));
    }

    public boolean checkIfUserExists(String userEmail) {
        return userRepository.checkIfUserExists(userEmail);
    }

    public void remindPassword(String userEmail) {
        User user = userRepository.getUserByEmail(userEmail);
        user.setTokenCreationDate(LocalDateTime.now());

        String token = tokenGenerator.generateToken();
        user.setPasswordToken(token);
        userRepository.update(user);

        sendEmail(userEmail, token);
    }

    public void changeUserPasswordAndDeleteToken(String userEmail, String password) {
        User user = userRepository.getUserByEmail(userEmail);

        user.setPassword(passwordEncoder.encode(password));
        user.setTokenCreationDate(null);
        user.setPasswordToken(null);

        userRepository.update(user);
    }

    public UserTokenTuple getTokenCreationDate(String token) {
        return userRepository.getTokenCreationDate(token);
    }

    //TODO: apgalvoti, ar emailo siuntimas turi vykti kitoje transakcijoje, ar ne
    private void sendEmail(String email, String token) {
        String emailContent = emailContentGenerator.createPasswordRemindEmailBody(token);
        emailProvider.sendSimpleMailTo(email, "Slaptažodžio pakeitimas", emailContent);
    }

    @Override
    public List<UserView> getAllUsers() {
        return userMapper.toViews(userRepository.findAll());
    }
}