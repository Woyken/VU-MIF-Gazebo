package lt.vu.mif.Config;

import lt.vu.mif.Entity.User;
import lt.vu.mif.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TempUserConfig implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String...args) {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test");
        userService.save(user);
    }
}
