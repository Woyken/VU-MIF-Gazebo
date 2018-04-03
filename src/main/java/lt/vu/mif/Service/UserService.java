package lt.vu.mif.Service;

import javax.inject.Named;
import lt.vu.mif.Entity.Roles.Role;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Named("UserService")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean isLoggedIn () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public User getLoggedUser() {
        if (isLoggedIn()) {
            return userRepository.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.getUserByEmail(username);
    }

}
