package lt.vu.mif;

import lt.vu.mif.Test.DataGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lt.vu.mif.Configuration.ServletConfiguration;
import org.springframework.context.annotation.Import;

@Import({ServletConfiguration.class})
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class);
        DataGenerator dataGenerator = context.getBean(DataGenerator.class);

        dataGenerator.insertProducts();
        dataGenerator.insertUsers();
    }
}