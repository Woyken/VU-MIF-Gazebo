package lt.vu.mif.Utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SkuCodeUtilsTests {

    private static final String TEST_FIRST_STRING = "Samsung Galaxy S5";
    private static final String TEST_SECOND_STRING = "Adidas powerboots xh52";

    @Test
    public void validateSku_validatingSku_success() {
        String firstResult = SkuCodeUtils.generateSkuCode(TEST_FIRST_STRING);
        String secondResult = SkuCodeUtils.generateSkuCode(TEST_SECOND_STRING);

        Assertions.assertEquals(firstResult, "Sams_Gala_S5");
        Assertions.assertEquals(secondResult, "Adid_powe_xh5");
    }
}

