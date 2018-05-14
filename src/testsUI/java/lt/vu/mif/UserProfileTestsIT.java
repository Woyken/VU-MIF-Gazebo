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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserProfileTestsIT {

    @LocalServerPort
    int localServerPort;

    @Autowired
    DataGenerator dataGenerator;

    private WebDriver driver;

    @Before
    public void before() {
        driver = new FirefoxDriver();
        driver.get("http://localhost:" + localServerPort + "/");
        dataGenerator.insertUsers();
    }

    @After
    public void after() {
        driver.quit();
    }

    public void loginFromCurrentWindow(WebDriver driver, String username, String password) {
        Actions action = new Actions(driver);
        WebElement dropDown = driver.findElement(By.linkText("Paskyra"));
        action.moveToElement(dropDown).pause(500).perform();
        driver.findElement(By.linkText("Prisijungti")).click();
        //In login page enter credentials
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        //Click login
        driver.findElement(By.xpath("/html/body/form/div/div/div/div[4]/div/input")).click();
    }

    @Test
    public void registration_success() {
        Actions action = new Actions(driver);
        //Load main page
        Assertions.assertTrue(driver.getTitle().equals("Home"));

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
        loginFromCurrentWindow(driver, "thisisemail@email.com", "Q!w2erty");
        //Check if in main page
        Assertions.assertTrue(driver.getTitle().equals("Home"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/main.xhtml"));
    }

    @Test
    public void changePassword_success() {
        Actions action = new Actions(driver);
        //Login with user
        loginFromCurrentWindow(driver, "user", "user");
        //Move cursor to profile drop down
        WebElement dropDown = driver.findElement(By.linkText("Paskyra"));
        action.moveToElement(dropDown).pause(500).perform();
        //Change password window
        driver.findElement(By.linkText("Keisti slaptažodį")).click();
        //enter credentials
        driver.findElement(By.xpath("/html/body/div[3]/div/form/div/div/div[1]/div/label/input"))
            .sendKeys("user");
        driver.findElement(By.xpath("/html/body/div[3]/div/form/div/div/div[2]/div[1]/label/input"))
            .sendKeys("Q!w2ertyNew");
        driver.findElement(By.xpath("/html/body/div[3]/div/form/div/div/div[3]/div/label/input"))
            .sendKeys("Q!w2ertyNew");
        driver.findElement(By.xpath("/html/body/div[3]/div/form/div/div/div[5]/div/input")).click();
        //check changed message
        WebElement resultLabel = new WebDriverWait(driver, 3).until(ExpectedConditions
            .visibilityOfElementLocated(
                By.xpath("/html/body/div[3]/div/form/div/div/div[5]/div/label")));
        Assertions.assertEquals("Slaptažodis sėkmingai pakeistas", resultLabel.getText());
        //Close change password modular
        driver.findElement(By.xpath("/html/body/div[3]/div/form/button")).click();
        //Logout
        driver.findElement(By.linkText("Atsijungti")).click();
        //Do another login
        loginFromCurrentWindow(driver, "user", "Q!w2ertyNew");
        //Check if in main page
        Assertions.assertTrue(driver.getCurrentUrl().contains("/main.xhtml"));
    }

    @Test
    public void changeEmail_success() {
        String currentEmail = "user1@gmail.com";
        String currentPassword = "password";
        Actions action = new Actions(driver);
        //Login with user
        loginFromCurrentWindow(driver, currentEmail, currentPassword);
        //Move cursor to profile drop down
        WebElement dropDown = driver.findElement(By.linkText("Paskyra"));
        action.moveToElement(dropDown).pause(500).perform();
        //Change email window
        driver.findElement(By.linkText("Keisti el. paštą")).click();
        //Current email correct?
        Assertions.assertEquals(currentEmail, driver
            .findElement(By.xpath("/html/body/div[4]/div/div/div/form/div[1]/div/label/input"))
            .getAttribute("value"));
        String newEmail = "myNewEmail@email.com";
        driver.findElement(By.xpath("/html/body/div[4]/div/div/div/form/div[2]/div/label/input"))
            .sendKeys(newEmail);
        driver.findElement(By.xpath("/html/body/div[4]/div/div/div/form/div[3]/div/label/input"))
            .sendKeys(newEmail);
        driver.findElement(By.xpath("/html/body/div[4]/div/div/div/form/div[4]/div/label/input"))
            .sendKeys(currentPassword);
        //Wait for ajax to finish
        WebElement resultLabel = new WebDriverWait(driver, 3).until(ExpectedConditions
            .visibilityOfElementLocated(
                By.xpath("/html/body/div[4]/div/div/div/form/div[6]/div/label")));
        //check message
        Assertions.assertEquals("El. paštas sėkmingai pakeistas", resultLabel.getText());
        //Close modal
        driver.findElement(By.xpath("/html/body/div[4]/div/button")).click();
        //Logout
        driver.findElement(By.linkText("Atsijungti")).click();
        //Do another login
        loginFromCurrentWindow(driver, newEmail, currentPassword);
        //Check if in main page
        Assertions.assertTrue(driver.getCurrentUrl().contains("/main.xhtml"));
    }
}
