package lt.vu.mif.authentication;

import lt.vu.mif.model.user.User;
import lt.vu.mif.repository.repository.implementations.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);

        if (null == user) {
            throw new UsernameNotFoundException("user with provided email does not exist");
        }

        return user;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public User getLoggedUser() {
        if (isLoggedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (null == authentication) {
                return null;
            }
            return userRepository.getUserByEmail(authentication.getName());
        }
        return null;
    }

    public String getLoggedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
