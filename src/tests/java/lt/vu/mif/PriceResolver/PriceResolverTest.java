package lt.vu.mif.PriceResolver;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Discount;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.ui.helpers.implementations.PriceResolver;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@Rollback
@SpringBootTest
@RunWith(SpringRunner.class)
public class PriceResolverTest {

    @Autowired
    private PriceResolver priceResolver;


    @Test
    public void noDiscountTest() {
        Product product = getDefaultProduct();
        Assertions
            .assertEquals(product.getPrice(), priceResolver.resolvePriceWithDiscount(product));
    }

    @Test
    public void onlyProductPercentageDiscountTest() {
        Product product = getDefaultProduct();

        Discount discount = getDefaultDiscount();
        discount.setPercentageDiscount(20L);
        product.setDiscount(discount);

        Assertions.assertEquals(getPriceWithPercentages(product, discount.getPercentageDiscount()),
            priceResolver.resolvePriceWithDiscount(product));
    }

    @Test
    public void onlyProductAbsoluteDiscountTest() {
        Product product = getDefaultProduct();

        Discount discount = getDefaultDiscount();
        discount.setAbsoluteDiscount(new BigDecimal(5L));

        product.setDiscount(discount);

        Assertions
            .assertEquals(discount.getAbsoluteDiscount(),
                priceResolver.resolvePriceWithDiscount(product));
    }

    @Test
    public void productWithCategoryDiscountTest() {
        Product product = getDefaultProduct();
        Category category = new Category("DEFAULT");

        Discount categoryDiscount = getDefaultDiscount();
        categoryDiscount.setPercentageDiscount(90L);
        //categoryDiscount.setAbsoluteDiscount(new BigDecimal("0.01"));
        category.setDiscount(categoryDiscount);

        product.setCategory(category);

        Assertions.assertEquals(
            getPriceWithPercentages(product, categoryDiscount.getPercentageDiscount()),
            priceResolver.resolvePriceWithDiscount(product));

    }

    @Test
    public void onlyProductBothDiscountsTest() {
        Product product = getDefaultProduct();

        Discount discount = new Discount();
        discount.setAbsoluteDiscount(new BigDecimal(1));
        discount.setPercentageDiscount(90L);
        discount.setFrom(LocalDateTime.now());
        discount.setTo(LocalDateTime.now().plusDays(1));

        product.setDiscount(discount);

        Assertions
            .assertEquals(discount.getAbsoluteDiscount(),
                priceResolver.resolvePriceWithDiscount(product));

        discount.setAbsoluteDiscount(new BigDecimal(8));
        discount.setPercentageDiscount(15L);

        Assertions.assertEquals(getPriceWithPercentages(product, discount.getPercentageDiscount()),
            priceResolver.resolvePriceWithDiscount(product));
    }


    private Product getDefaultProduct() {
        Product newProduct = new Product();
        newProduct.setTitle("Very long title");
        newProduct.setDescription("Description 1");
        newProduct.setPrice(new BigDecimal(10L));
        newProduct.setSku("sku1");
        newProduct.setCategory(new Category("DEFAULT"));

        return newProduct;
    }

    private Discount getDefaultDiscount() {
        Discount discount = new Discount();
        discount.setFrom(LocalDateTime.now());
        discount.setTo(LocalDateTime.now().plusDays(10));
        return discount;
    }

    private BigDecimal getPriceWithPercentages(Product product, Long value) {
        return product.getPrice().multiply(new BigDecimal(100 - value)).divide(new BigDecimal(100));
    }
}
