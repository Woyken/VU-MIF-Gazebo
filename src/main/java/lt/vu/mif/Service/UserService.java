package lt.vu.mif.Service;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;

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

    public String getLoggedUserUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);

        if(null == user) {
            throw new UsernameNotFoundException("User with provided email does not exist");
        }

        return user;
    }

}
