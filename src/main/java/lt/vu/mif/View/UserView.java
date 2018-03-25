package lt.vu.mif.View;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Roles.Role;

@Named
@Getter
@Setter
public class UserView {
    private Long id;
    private String email;
    private Role role;

    public UserView() {
    }

    public UserView(Long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
