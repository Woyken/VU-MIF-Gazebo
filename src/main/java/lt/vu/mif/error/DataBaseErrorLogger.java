package lt.vu.mif.error;

import java.time.LocalDateTime;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.model.error.Error;
import lt.vu.mif.model.user.User;
import lt.vu.mif.repository.repository.interfaces.IErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseErrorLogger implements IErrorLogger {

    @Autowired
    private UserService userService;

    @Autowired
    private IErrorRepository errorRepository;

    @Override
    public void logError(String errorText) {
        Error error = new Error();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMessage(errorText);
        error.setLoggedUserEmail(errorText);

        User loggedUser = userService.getLoggedUser();
        error.setLoggedUserEmail(loggedUser == null ? null : loggedUser.getEmail());

        errorRepository.save(error);
    }
}