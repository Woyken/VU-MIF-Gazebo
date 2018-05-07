package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.user.Role;

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
}