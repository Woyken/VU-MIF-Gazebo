package lt.vu.mif.Controller;

import lombok.Getter;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Getter
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private List<UserView> users = new ArrayList<>();

    public void onPageLoad() {
        users = userRepository.getAll().stream().map(UserView::new).collect(Collectors.toList());
    }

}