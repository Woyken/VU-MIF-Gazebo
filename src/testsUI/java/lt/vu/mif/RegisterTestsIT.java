package lt.vu.mif;

import lt.vu.mif.generator.DataGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegisterTestsIT {

    @LocalServerPort
    int localServerPort;

    @Autowired
    DataGenerator dataGenerator;

    private WebDriver driver;

    @Before
    public void before() {
        driver = new FirefoxDriver();
        dataGenerator.insertProducts();
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void registration_success() {
        Actions action = new Actions(driver);
        //Load main page
        driver.get("http://localhost:" + localServerPort + "/");
        String title = driver.getTitle();
        Assertions.assertTrue(title.equals("Home"));

        //Navigate to register
        WebElement dropDown = driver.findElement(By.linkText("Paskyra"));
        action.moveToElement(dropDown).pause(500).perform();
        driver.findElement(By.linkText("Registruotis")).click();
        //In register page enter credentials
        driver.findElement(
            By.xpath("//*[@id=\"registration-window\"]/div/div/form/div[1]/div/label/input"))
            .sendKeys("thisisemail@email.com");
        driver.findElement(
            By.xpath("//*[@id=\"registration-window\"]/div/div/form/div[2]/div[1]/label/input"))
            .sendKeys("Q!w2erty");
        driver.findElement(
            By.xpath("//*[@id=\"registration-window\"]/div/div/form/div[3]/div/label/input"))
            .sendKeys("Q!w2erty");
        //Click register
        driver
            .findElement(By.xpath("//*[@id=\"registration-window\"]/div/div/form/div[5]/div/input"))
            .click();
        Assertions.assertTrue(driver.findElement(By.id("j_idt9:message")).getText()
            .contains("thisisemail@email.com sėkmingai sukurtas"));

        //TODO: Currently there's no way to navigate back. For now just directly move back.
        driver.get("http://localhost:" + localServerPort + "/");

        //Do login
        dropDown = driver.findElement(By.linkText("Paskyra"));
        action.moveToElement(dropDown).pause(500).perform();
        driver.findElement(By.linkText("Prisijungti")).click();
        //In login page enter credentials
        driver.findElement(By.id("username")).sendKeys("thisisemail@email.com");
        driver.findElement(By.id("password")).sendKeys("Q!w2erty");
        //Click login
        driver.findElement(By.xpath("/html/body/form/div/div/div/div[4]/div/input")).click();
        Assertions.assertTrue(title.equals("Home"));
    }
}
