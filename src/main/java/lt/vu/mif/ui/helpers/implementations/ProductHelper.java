package lt.vu.mif.ui.helpers.implementations;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.excel.ProductExcelReader;
import lt.vu.mif.model.product.Cart;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.model.user.User;
import lt.vu.mif.repository.repository.implementations.CartRepository;
import lt.vu.mif.repository.repository.interfaces.IBoughtProductRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.mappers.implementations.BoughtProductMapper;
import lt.vu.mif.ui.mappers.implementations.CartMapper;
import lt.vu.mif.ui.mappers.implementations.ProductMapper;
import lt.vu.mif.ui.mappers.implementations.UserMapper;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.ui.view.CartItemView;
import lt.vu.mif.ui.view.CartView;
import lt.vu.mif.ui.view.ProductSearchView;
import lt.vu.mif.ui.view.ProductView;
import lt.vu.mif.utils.interfaces.IProductParser;
import lt.vu.mif.utils.search.ProductSearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ProductHelper implements IProductHelper {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BoughtProductMapper boughtProductMapper;
    @Autowired
    private IMapper<ProductSearch, ProductSearchView> productSearchMapper;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductExcelReader productExcelReader;
    @Autowired
    private IProductParser productParser;
    @Autowired
    private IBoughtProductRepository boughtProductRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void update(ProductView view) {
        Product entity = productMapper.toEntity(view);
        productRepository.update(entity);
    }

    @Override
    public Page<BoughtProductView> getBoughtProductsPage(int activePage, int pageSize,
        Long userId) {
        return boughtProductRepository.getBoughtProductsPage(activePage, pageSize, userId)
            .map(boughtProductMapper::toView);
    }

    @Override
    public Page<BoughtProductView> getCurrentUserBoughtProductsPage(int activePage, int pageSize) {
        Long currentUserId = userService.getLoggedUser().getId();
        return getBoughtProductsPage(activePage, pageSize, currentUserId);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteMultipleByIds(List<Long> productIds) {
        productRepository.deleteAll(productIds);
    }

    @Override
    public void saveAll(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public CartItemView getCartProductView(Long productId) {
        return new CartItemView(productMapper.toView(productRepository.get(productId)));
    }

    @Override
    public CartView getCurrentUserCart() {
        User loggedInUser = userService.getLoggedUser();
        if (null == loggedInUser) {
            return null;
        }

        Cart usersCart = cartRepository.getByUserId(loggedInUser.getId());
        if (null == usersCart) {
            return null;
        }

        return cartMapper.toView(usersCart);
    }

    @Override
    @Transactional
    public CartView setCurrentUserCart(CartView cart) {
        User loggedInUser = userService.getLoggedUser();
        if (null == loggedInUser) {
            return null;
        }
        Cart existingCartEntity = cartRepository.getByUserId(loggedInUser.getId());
        if (null != existingCartEntity) {
            cart.setId(existingCartEntity.getId());
        }

        cart.setUser(userMapper.toView(loggedInUser));
        List<Product> products = productRepository
            .get(cart.getItems().stream().map(ProductView::getId).collect(
                Collectors.toList()));
        Cart cartEntity = cartMapper.toEntity(cart);
        Cart finalCartEntity = cartEntity;
        cartEntity.getItems().forEach(cartItem -> {
            Optional<Product> t = products.stream()
                .filter(product -> product.getId().equals(cartItem.getProduct().getId()))
                .findFirst();
            if (t.isPresent()) {
                cartItem.setProduct(t.get());
            } else {
                finalCartEntity.getItems().remove(cartItem);
            }
        });
        cartEntity = cartRepository.update(finalCartEntity);
        return cartMapper.toView(cartEntity);
    }

    @Override
    @Transactional
    public void currentUserClearCart() {
        User loggedInUser = userService.getLoggedUser();
        if (null == loggedInUser) {
            return;
        }

        Cart usersCart = cartRepository.getByUserId(loggedInUser.getId());
        cartRepository.delete(usersCart);
    }

    @Override
    public Page<ProductView> getProductsPage(int activePage, int pageSize,
        ProductSearchView search) {
        return productRepository
            .getProductsPage(productSearchMapper.toEntity(search), activePage, pageSize)
            .map(productMapper::toView);
    }

    @Override
    public void createNewProduct(ProductView newProduct) {
        Product product = productMapper.toEntity(newProduct);
        productRepository.save(product);
    }

    @Override
    public ProductView getProduct(Long productId) {
        return productMapper.toView(productRepository.get(productId));
    }

    @Override
    public CompletableFuture<ImportResult> importProducts(InputStream inputStream) {
        CompletionStage<ImportResult> productPromise = productExcelReader
            .readFile(inputStream);

        productPromise.thenAccept(importResult -> {
            List<Product> productsToSave = productParser.parseProducts(importResult);
            saveAll(productsToSave);
        });

        return productPromise.toCompletableFuture();
    }

    @Override
    public BigDecimal getProductsSum(List<BoughtProductView> productViews) {
        BigDecimal totalSum = new BigDecimal(0L);

        for (BoughtProductView productView : productViews) {
            if (productView.getPrice() != null) {
                totalSum = totalSum.add(
                    productView.getPrice().multiply(new BigDecimal(productView.getQuantity())));
            }
        }

        return totalSum;
    }

    @Override
    public ProductView getProductViewFromNavigationQuery() {
        String productId = FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .getRequestParameterMap()
            .get("productId");

        // No ID in query
        if (StringUtils.isBlank(productId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        ProductView productView = this.getProduct(Long.valueOf(productId));

        if (productView == null) {
            throw new IllegalStateException("Product" + "with ID=" + productId + "not found");
        }

        return productView;
    }
}
