package lt.vu.mif.Utils;

import lt.vu.mif.Utils.ValidationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationUtilsTests {

    private static final String TEST_INVALID_PASSWORD = "neteisingas";
    private static final String TEST_VALID_PASSWORD = "Teisingas?";

    @Test
    void validatePassword_validatingPassword_success() {
        boolean invalidResult = ValidationUtils.ValidatePassword(TEST_INVALID_PASSWORD);
        boolean validResult = ValidationUtils.ValidatePassword(TEST_VALID_PASSWORD);

        Assertions.assertEquals(invalidResult, false);
        Assertions.assertEquals(validResult, true);
    }
}