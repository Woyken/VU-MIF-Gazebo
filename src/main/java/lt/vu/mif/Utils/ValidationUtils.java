package lt.vu.mif.Utils;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {

    private final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|(?=.*[$@!%*?&]))[a-zA-Z\\d$@!%*?&]{8,}$";

    public static boolean isPasswordValid(String password){
        return Pattern.matches(PASSWORD_REGEX, password);
    }


}
