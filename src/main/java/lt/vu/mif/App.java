package lt.vu.mif;

import lt.vu.mif.generator.interfaces.IDataGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"lt"})
@EnableAutoConfiguration
@ComponentScan(basePackages = {"lt"})
@ImportResource("classpath:beans.xml")
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class);

        IDataGenerator dataGenerator = (IDataGenerator) context.getBean("dataGenerator");
        dataGenerator.generateData();
    }
}