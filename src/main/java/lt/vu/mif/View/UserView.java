package lt.vu.mif.View;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@Getter
@Setter
public class UserView {
    private Long id;
    private String email;

    public UserView() {
    }

    public UserView(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
