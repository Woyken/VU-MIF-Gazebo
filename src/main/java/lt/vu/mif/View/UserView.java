package lt.vu.mif.View;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Roles.Role;
import lt.vu.mif.Entity.User;

import javax.inject.Named;

@Named
@Getter
@Setter
public class UserView {

    private Long id;
    private String email;
    private String password;
    private Role role;
    private boolean blocked;
    private Long ordersCount;
    private Long averageCount;
    private Long ordersSum;

    public UserView() {
    }

    public UserView(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.blocked = user.isBlocked();
    }
}
