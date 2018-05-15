package lt.vu.mif.generator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.OrderStatus;
import lt.vu.mif.model.product.Image;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.model.user.Role;
import lt.vu.mif.model.user.User;
import lt.vu.mif.repository.repository.implementations.Category;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.repository.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void insertUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            User user = new User();
            user.setPassword(passwordEncoder.encode("password"));
            if (i % 2 == 0) {
                user.setEmail("admin" + i + "@gmail.com");
                user.setRole(Role.ADMIN);
            } else {
                user.setEmail("user" + i + "@gmail.com");
                user.setRole(Role.USER);
            }
            users.add(user);
        }

        users.addAll(generateSimpleUsers());

        userRepository.saveAll(users);
    }

    private List<User> generateSimpleUsers() {
        User user = new User();
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user");
        user.setRole(Role.USER);

        User admin = new User();
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin");
        admin.setRole(Role.ADMIN);

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(admin);

        return users;
    }

    private User generateSimpleUser() {
        User user = new User();
        user.setPassword("user");
        user.setEmail("user");
        user.setRole(Role.USER);

        return user;
    }

    public void insertProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            addProducts(products);
        }

        productRepository.saveAll(products);
    }

    private void addProducts(List<Product> products) {
        for (int i = 1; i <= 9; i++) {
            Product product = new Product();
            product.setDescription(getProductDescription(i));
            product.setPrice(new BigDecimal(i * 5));
            product.setTitle(getProductTitle(i));
            product.setSku(UUID.randomUUID().toString());
            product.getImages().add(getImage("static/images/products/shoe-" + i + ".jpg"));
            product.getImages().add(getImage("static/images/products/shoe-1.jpg"));
            product.getImages().add(getImage("static/images/products/shoe-2.jpg"));
            product.getImages().add(getImage("static/images/products/shoe-3.jpg"));
            product.getImages().add(getImage("static/images/products/shoe-4.jpg"));

            product.setCategory(i % 2 == 0 ? createFurnitureCategory() : createSportCategory());
            products.add(product);
        }
    }

    private Category createFurnitureCategory() {
        Category furniture = new Category();
        furniture.setName("furniture");

        Category kitchen = new Category();
        kitchen.setName("kitchen");

        Category bedroom = new Category();
        bedroom.setName("bedroom");

        bedroom.setParentCategory(furniture);
        bedroom.setParentCategory(kitchen);

        return furniture;
    }

    private Category createSportCategory() {
        Category sport = new Category();
        sport.setName("sport");

        Category basketball = new Category();
        basketball.setName("basketball");

        Category tennis = new Category();
        tennis.setName("tennis");

        basketball.setParentCategory(sport);
        tennis.setParentCategory(sport);

        return sport;
    }

    private String getProductDescription(int index) {
        if (index % 2 == 0) {
            return "Šie vyriški batai iš Gallus kolekcijos garantuoja patogumą ir elegantiškumą tuo pat metu. Juoda, kokybiška oda kartu su klasikiniu batų modeliu daro batus solidžius. "
                    + "Su jais puikiai atrodysite ne tik kasdien, bet ir norėdami pasipuošti. ";
        } else if (index % 3 == 0) {
            return "Šie vyriški batais yra vienas stilingiausių ir elegantiškiausių pusbačių modelių Gazebo parduotuvėse. Ruda jų spalva kartu su mėlynu varstymu puikiai derės ne tik prie klasikinių "
                    + "džinsų, bet net ir prie kostiuminių kelnių norint pasipuošti. Tad galite neabejoti, jog su šiais batais atrodysite tikrai pribloškiamai bet kokia proga. ";
        } else if (index % 5 == 0) {
            return "Šie vyriški Venice kolekcijos batai yra vienas geriausių pasirinkimų ieškant elegantiškų, tačiau itin patogių batų. Klasikinis batų modelis kartu su varstymu priekyje suteikia "
                    + "solidumo ir stilingumo. O storesnis batų modelis daro pusbačius itin komfortiškais ir šiuolaikiškais.";
        }
        return "Šie pusbačiai yra sukurti garantuoti Jūsų patogumą. Storesnis padas kartu su kilpiniu audiniu sukuria komfortišką ir unikalų modelį. O stilingumo ir išskirtinumo jam teikia "
                + "mėlyna spalva kartu su baltos ir raudonos spalvų detalėmis. Šie pusbačiai puikiai tiks avėti kasdien kartu su tamsiomis kelnėmis ar klasikiniais mėlynais džinsais.";
    }

    private String getProductTitle(int index) {
        if (index % 2 == 0) {
            return "Mephis One";
        } else if (index % 3 == 0) {
            return "AM SHOE";
        } else if (index % 5 == 0) {
            return "Highland Creek";
        }
        return "Claudio Conti";
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

    public void insertOrders() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            addOrders(orders);
        }

        orderRepository.saveAll(orders);
    }

    private void addOrders(List<Order> orders) {
        for (int i = 1; i <= 9; i++) {
            Order order = new Order();
            order.setRating(5L);
            order.setCreationDate(LocalDateTime.now());
            order.setStatus(OrderStatus.ACCEPTED);
            order.setUser(generateSimpleUser());
            orders.add(order);
        }
    }
}