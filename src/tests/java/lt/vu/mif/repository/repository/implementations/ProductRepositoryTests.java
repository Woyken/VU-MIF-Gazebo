package lt.vu.mif.repository.repository.implementations;

import lt.vu.mif.model.product.Product;
import lt.vu.mif.utils.search.ProductSearch;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    private boolean productsEqual(Product product1, Product product2) {
        return product1.getSku().equals(product2.getSku()) &&
                product1.getPrice().equals(product2.getPrice()) &&
                product1.getTitle().equals(product2.getTitle()) &&
                product1.getDescription().equals(product2.getDescription());
    }

    private boolean checkProducts(List<Product> expected, List<Product> got) {
        for (Product expectedProduct : expected) {
            boolean found = false;
            for (Product gotProduct : got) {
                if (productsEqual(expectedProduct, gotProduct)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Rollback
    @Transactional
    @Test
    public void get_gettingProducts_gotCorrectProducts() {
        List<Product> expectedProducts = new ArrayList<>();
        Product product = new Product();
        product.setTitle("Title1");
        product.setDescription("Description1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("skuCode1");
        Long expectedId1 = productRepository.save(product).getId();

        expectedProducts.add(product);

        product = new Product();
        product.setTitle("Title2");
        product.setDescription("Description2");
        product.setPrice(new BigDecimal(20L));
        product.setSku("skuCode2");
        Long expectedId2 = productRepository.save(product).getId();

        expectedProducts.add(product);

        List<Product> gotProducts = new ArrayList<>();
        gotProducts.add(productRepository.get(expectedId1));
        gotProducts.add(productRepository.get(expectedId2));

        Assertions.assertTrue(checkProducts(expectedProducts, gotProducts));
    }

    @Rollback
    @Transactional
    @Test
    public void getList_gettingProducts_gotCorrectProducts() {
        List<Long> expectedIds = new ArrayList<>();
        List<Product> expectedProducts = new ArrayList<>();
        Product product = new Product();
        product.setTitle("Title1");
        product.setDescription("Description1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("skuCode1");
        expectedIds.add(productRepository.save(product).getId());

        expectedProducts.add(product);

        product = new Product();
        product.setTitle("Title2");
        product.setDescription("Description2");
        product.setPrice(new BigDecimal(20L));
        product.setSku("skuCode2");
        expectedIds.add(productRepository.save(product).getId());

        expectedProducts.add(product);

        List<Product> gotProducts = productRepository.get(expectedIds);

        Assertions.assertTrue(checkProducts(expectedProducts, gotProducts));
    }


    @Rollback
    @Test
    public void deleteAll_softDeletingProduct_productSoftDeleted() {
        Product product = new Product();
        product.setTitle("Title1");
        product.setDescription("Description1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("skuCode1");
        Long productId = productRepository.save(product).getId();

        List<Long> deleteIds = new ArrayList<>();
        deleteIds.add(productId);
        productRepository.deleteAll(deleteIds);
        Product resultProduct = productRepository.get(productId);
        Assertions.assertTrue(resultProduct.isDeleted());
    }

    @Rollback
    @Transactional
    @Test
    public void deleteAll_softDeletingInvalidId_productDoesntExist() {
        List<Long> deleteIds = new ArrayList<>();
        deleteIds.add(1L);
        productRepository.deleteAll(deleteIds);
        Product resultProduct = productRepository.get(1L);
        Assertions.assertNull(resultProduct);
    }

    @Rollback
    @Transactional
    @Test
    public void getProductsPage_() {
        Product product = new Product();
        product.setTitle("Title1");
        product.setDescription("Description1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("skuCode1");
        productRepository.save(product);

        product = new Product();
        product.setTitle("Title2");
        product.setDescription("Description2");
        product.setPrice(new BigDecimal(20L));
        product.setSku("skuCode2");
        productRepository.save(product);

        ProductSearch productSearch = new ProductSearch();
        Page<Product> productsPage = productRepository
                .getProductsPage(productSearch, 0, 1);
        Assertions.assertEquals(2, productsPage.getTotalPages());
        Assertions.assertEquals(2, productsPage.getTotalElements());
        Assertions.assertEquals(1, productsPage.getSize());
    }

}
