package lt.vu.mif.ui.helpers.interfaces;

import java.math.BigDecimal;
import lt.vu.mif.model.product.Product;

public interface IPriceResolver {

    BigDecimal resolvePriceWithDiscount(Product product);
}