package lt.vu.mif.View;

import javax.inject.Named;

/**
 * Created by monika on 18.3.20.
 */
@Named
public class UserView {
    private Long id;
    private String email;

    public UserView() {
    }

    public UserView(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
