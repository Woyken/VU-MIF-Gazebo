package lt.vu.mif.utils.validation;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {

    private final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\]))[a-zA-Z\\d!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\]{8,}$";
    private final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final String PAYMENT_CARD_REGEX = "^\\d{16}$";
    private final String YEAR_REGEX = "^\\d{2}$";
    private final String MONTH_REGEX = "^(0?[1-9]|1[012])$";
    private final String CVS_REGEX = "^\\d{3}$";
    private final String PRICE_REGEX = "[0-9]+([,.][0-9]{1,2})?";
    private final String AMOUNT_REGEX = "^[1-9]\\d*$";
    public final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private final String TIME_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    public static boolean isPasswordValid(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isEmailValid(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isPaymentCardValid(String card) {
        return Pattern.matches(PAYMENT_CARD_REGEX, card);
    }

    public static boolean isYearValid(String year) {
        return Pattern.matches(YEAR_REGEX, year);
    }

    public static boolean isMonthValid(String month) {
        return Pattern.matches(MONTH_REGEX, month);
    }

    public static boolean isCvsValid(String cvs) {
        return Pattern.matches(CVS_REGEX, cvs);
    }

    public static boolean isPriceValid(String price) { return Pattern.matches(PRICE_REGEX, price); }

    public static boolean isAmountValid(String amount) {
        return Pattern.matches(AMOUNT_REGEX, amount);
    }

    public static boolean isTimeValid(String time) {
        return Pattern.matches(TIME_REGEX, time);
    }
}
