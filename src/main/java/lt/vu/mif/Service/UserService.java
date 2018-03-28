package lt.vu.mif.Service;

import lt.vu.mif.Entity.Roles.Role;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.getUserByEmail(username);
    }

}
