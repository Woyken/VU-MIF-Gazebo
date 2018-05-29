package lt.vu.mif.ui.helpers.interfaces;

import lt.vu.mif.model.product.Product;

import java.math.BigDecimal;

public interface IPriceResolver {

    BigDecimal resolvePriceWithDiscount(Product product);
}