package lt.vu.mif.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    private List<UserView> users = new ArrayList<>();

    public void onPageLoad() {
        users = userRepository.getAll().stream().map(UserView::new).collect(Collectors.toList());
    }

    public void blockUser(UserView userView) {
        userRepository.blockUser(userView.getId(), !userView.isBlocked());
    }
}