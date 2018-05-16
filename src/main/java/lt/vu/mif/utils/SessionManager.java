package lt.vu.mif.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionManager {

    public void setAttribute(String attribute, Object value) {
        getSession().setAttribute(attribute, value);
    }


    public Object getAttribute(String attribute) {
        return getSession().getAttribute(attribute);
    }

    public void removeAttribute(String attribute) {
        getSession().removeAttribute(attribute);
    }

    private HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        return request.getSession();
    }
}