package lt.vu.mif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lt.vu.mif.generator.DataGenerator;

@SpringBootApplication(scanBasePackages = {"lt"})
@EnableJpaRepositories(basePackages = {"lt"})
@EntityScan(basePackages = {"lt"})
@EnableAutoConfiguration
@ComponentScan(basePackages = {"lt"})
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class);
        DataGenerator dataGenerator = context.getBean(DataGenerator.class);

        dataGenerator.insertProducts();
        dataGenerator.insertUsers();
    }
}