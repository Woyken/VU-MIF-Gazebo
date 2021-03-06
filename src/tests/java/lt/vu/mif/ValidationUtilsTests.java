package lt.vu.mif;

import lt.vu.mif.utils.validation.ValidationUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ValidationUtilsTests {

    private static final String TEST_INVALID_PASSWORD = "neteisingas";
    private static final String TEST_VALID_PASSWORD = "Teisingas?";

    @Test
    public void validatePassword_validatingPassword_success() {
        boolean invalidResult = ValidationUtils.isPasswordValid(TEST_INVALID_PASSWORD);
        boolean validResult = ValidationUtils.isPasswordValid(TEST_VALID_PASSWORD);

        Assertions.assertFalse(invalidResult);
        Assertions.assertTrue(validResult);
    }
}