package lt.vu.mif.View;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;

import javax.inject.Named;

@Named
@Getter
@Setter
public class UserView {

    private Long id;
    private String email;
    private String password;

    public UserView() {
    }

    public UserView(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
