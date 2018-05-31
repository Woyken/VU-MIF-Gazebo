package lt.vu.mif;

import lt.vu.mif.generator.interfaces.IDataGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartTestsIT {

    @LocalServerPort
    int localServerPort;

    @Autowired
    IDataGenerator mockDataGenerator;

    private WebDriver driver;

    @Before
    public void before() {
        driver = new FirefoxDriver();
        if (!TestPreparation.getSetuped("dataGenerator.insertProducts")) {
            mockDataGenerator.generateData();
            TestPreparation.setSetuped("dataGenerator.insertProducts");
        }
        driver.get("http://localhost:" + localServerPort + "/");
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void addingProductsToCart_success() {
        //Add first product to cart
        driver
            .findElement(By.xpath("/html/body/div[1]/div[7]/div/span/div[1]/div[1]/form/input[2]"))
            .click();
        //Go to cart
        driver.findElement(By.id("cart-button")).click();
        //Check cart item amount
        WebElement cartItemAmount = driver
            .findElement(By.xpath("//*[@id=\"shopping-cart-items-form\"]/div[2]/div[2]/input"));
        Assertions.assertEquals("1", cartItemAmount.getAttribute("value"));

        //Click remove from cart
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-items-form\"]/div[2]/div[4]/a"))
            .click();
        //click Yes
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div[1]/form/a")).click();
        // make sure item is gone.
        Assertions.assertEquals(0,
            driver.findElement(By.id("shopping-cart-items-form")).findElements(By.tagName("img"))
                .size());
        //back to catalog
        driver.findElement(By.linkText("Atgal į katalogą")).click();
        //open first item description
        driver.findElement(By.xpath("/html/body/div[1]/div[7]/div/span/div[1]/div[1]/a")).click();
        //set amount to 5
        WebElement inputAmount = driver
            .findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/form/div[2]/div[2]/input"));
        inputAmount.clear();
        inputAmount.sendKeys("5");
        //Click on add to cart
        driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/form/input[3]")).click();
        //Go to cart
        driver.findElement(By.id("cart-button")).click();
        //Check cart item amount
        cartItemAmount = driver
            .findElement(By.xpath("//*[@id=\"shopping-cart-items-form\"]/div[2]/div[2]/input"));
        Assertions.assertEquals("5", cartItemAmount.getAttribute("value"));
    }
}
