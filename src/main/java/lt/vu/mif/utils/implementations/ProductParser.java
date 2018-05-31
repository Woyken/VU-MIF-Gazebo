package lt.vu.mif.utils.implementations;

import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.excel.ExcelProduct;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.CategoryAttribute;
import lt.vu.mif.model.product.CategoryAttributeValue;
import lt.vu.mif.model.product.Image;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.model.product.ProductAttributeValue;
import lt.vu.mif.repository.repository.implementations.CategoryRepository;
import lt.vu.mif.utils.interfaces.IProductParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ProductParser implements IProductParser {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> parseProducts(ImportResult importResult) {
        List<Product> result = new ArrayList<>();

        for (ExcelProduct excelProduct : importResult.getProducts()) {
            Product product = new Product();
            product.setSku(excelProduct.getSkuCode());
            product.setTitle(excelProduct.getTitle());
            product.setDescription(excelProduct.getDescription());
            product.setPrice(excelProduct.getPrice());
            product.setCategory(excelProduct.getCategory());

            for (byte[] bytes : excelProduct.getImagesBytes()) {
                product.getImages().add(new Image(bytes));
            }
            parseAttributes(excelProduct, product);

            result.add(product);
        }
        return result;
    }

    private void parseAttributes(ExcelProduct product, Product entity) {
        Category category = categoryRepository.get(entity.getCategory().getId());
        while (category != null) {
            for (CategoryAttribute categoryAttribute : category.getAttributes()) {
                String valueForAttribute = product.getAttributes().get(categoryAttribute.getName());

                CategoryAttributeValue value = null;
                for (CategoryAttributeValue val : categoryAttribute.getValues()) {
                    if (val.getValue().equals(valueForAttribute)) {
                        value = val;
                        break;
                    }
                }

                if (value != null) {
                    ProductAttributeValue productAttributeValue = new ProductAttributeValue();
                    productAttributeValue.setCategoryAttributeValue(value);
                    productAttributeValue.setProduct(entity);
                    entity.getAttributeValues().add(productAttributeValue);
                }
            }

            category = category.getParentCategory();
        }
    }
}