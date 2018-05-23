package lt.vu.mif.ui.helpers.implementations;

import java.math.BigDecimal;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Discount;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.ui.helpers.interfaces.IPriceResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class PriceResolver implements IPriceResolver {

    private static final int ONE_HUNDRED = 100;

    @Override
    public BigDecimal resolvePriceWithDiscount(Product product) {
        BigDecimal priceWithCategoryDiscount = resolvePriceWithCategoryDiscount(product);
        BigDecimal priceWithProductDiscount = resolvePriceWithProductDiscount(product);

        if (priceWithCategoryDiscount != null) {
            return priceWithCategoryDiscount.compareTo(priceWithProductDiscount) > 0
                ? priceWithCategoryDiscount : priceWithProductDiscount;
        }

        return priceWithProductDiscount;
    }

    private BigDecimal resolvePriceWithProductDiscount(Product product) {
        if (product.getDiscount() == null || !product.getDiscount().isDiscountValid()) {
            return product.getPrice();
        }

        Discount discount = product.getDiscount();

        BigDecimal priceWithAbsoluteDiscount = null;
        BigDecimal priceWithPercentage = null;

        if (discount.getAbsoluteDiscount() != null) {
            priceWithAbsoluteDiscount = discount.getAbsoluteDiscount();
        }

        if (discount.getPercentageDiscount() != null) {
            priceWithPercentage = getPriceWithPercentages(product,
                discount.getPercentageDiscount());
        }

        BigDecimal resolvedPrice = getGreaterPrice(priceWithAbsoluteDiscount, priceWithPercentage);
        return resolvedPrice == null ? product.getPrice() : resolvedPrice;
    }

    private BigDecimal resolvePriceWithCategoryDiscount(Product product) {
        Category productCategory = product.getCategory();

        if (productCategory == null) {
            return null;
        }

        Discount categoryDiscount = productCategory.getDiscount();
        if (categoryDiscount == null || !categoryDiscount.isDiscountValid()) {
            return null;
        }

        BigDecimal priceWithPercentage = null;

        if (categoryDiscount.getPercentageDiscount() != null) {
            priceWithPercentage = getPriceWithPercentages(product,
                categoryDiscount.getPercentageDiscount());
        }

        return priceWithPercentage == null ? product.getPrice() : priceWithPercentage;
    }

    private BigDecimal getGreaterPrice(BigDecimal priceWithAbsoluteDiscount,
        BigDecimal priceWithPercentage) {
        if (priceWithAbsoluteDiscount != null && priceWithPercentage != null) {
            return priceWithAbsoluteDiscount.compareTo(priceWithPercentage) > 0
                ? priceWithAbsoluteDiscount : priceWithPercentage;
        } else if (priceWithAbsoluteDiscount != null) {
            return priceWithAbsoluteDiscount;
        } else if (priceWithPercentage != null) {
            return priceWithPercentage;
        }
        return null;
    }


    private BigDecimal getPriceWithPercentages(Product product, Long value) {
        return product.getPrice().multiply(new BigDecimal(ONE_HUNDRED - value))
            .divide(new BigDecimal(ONE_HUNDRED));
    }

    private BigDecimal subtract(BigDecimal bigDecimal, BigDecimal value) {
        return bigDecimal.subtract(value);
    }
}
