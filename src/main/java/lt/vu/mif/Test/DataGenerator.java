package lt.vu.mif.Test;

import lt.vu.mif.Entity.*;
import lt.vu.mif.Entity.Roles.Role;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public void insertUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            User user = new User();
            user.setPassword("password");
            if (i % 2 == 0) {
                user.setEmail("admin" + i + "@gmail.com");
                user.setRole(Role.ADMIN);
            } else {
                user.setEmail("user" + i + "@gmail.com");
                user.setRole(Role.USER);
            }
            users.add(user);
        }

        userRepository.save(users);
    }

    public void insertProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            Product product = new Product();
            product.setDescription("description" + i);
            product.setPrice(new BigDecimal(i * 5));
            product.setTitle("title" + i);
            product.getImages().add(getImage("static/images/products/shoe-" + i + ".jpg"));
            products.add(product);
        }

        productRepository.save(products);
    }

    private Image getImage(String path) {
        Image image = null;
        try {
            File file = new ClassPathResource(path).getFile();
            byte[] content = Files.readAllBytes(file.toPath());
            image = new Image();
            image.setContent(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }
}