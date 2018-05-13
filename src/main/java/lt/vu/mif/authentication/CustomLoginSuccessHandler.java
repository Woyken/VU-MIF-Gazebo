package lt.vu.mif.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lt.vu.mif.ui.controller.CartController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CartController cartController;

    public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {

        if (cartController.getRedirectAfterLogin()) {
            cartController.setRedirectAfterLogin(false);
            getRedirectStrategy().sendRedirect(request, response, "/cart.xhtml");
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
