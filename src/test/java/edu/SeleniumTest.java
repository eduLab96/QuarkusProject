package edu;

import edu.entities.Product;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import io.github.bonigarcia.wdm.WebDriverManager;

@QuarkusTest
public class SeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws InterruptedException {
        //this line gets you the chrome driver assuming you have Chrome installed on you computer
        driver = WebDriverManager.chromedriver().create();
        driver.get("http://localhost:8080");
        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Products"));
    }

    @Test
    public void testAddButton_gridContainsProduct() throws InterruptedException {
        Product testProduct = new Product("SeleniumTestName", "SeleniumTestDesc");

        WebElement addNameField = driver.findElement(By.id("addNameField"));
        addNameField.sendKeys(testProduct.getName());
        WebElement addDescField = driver.findElement(By.id("addDescriptionField"));
        addDescField.sendKeys(testProduct.getDescription());

        Thread.sleep(2000);  // Let the user actually see something!

        WebElement button = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add')]"));
        button.click();

        Thread.sleep(2000);  // Let the user actually see something!

        WebElement grid = driver.findElement(By.id("Grid"));
        Assertions.assertTrue(grid.getText().contains(testProduct.getName()));
    }

    @Test
    public void testUpdateButton_gridDoesntContainProduct() throws InterruptedException {
        Product testProduct = new Product("updatedName", "updatedDesc");
        WebElement updtNameField = driver.findElement(By.id("updtNameField"));
        updtNameField.sendKeys(testProduct.getName());
        WebElement updtDescField = driver.findElement(By.id("updtDescField"));
        updtDescField.sendKeys(testProduct.getDescription());

        var button = driver.findElement(By.xpath("//vaadin-button[contains(.,'Update')]"));
        button.click();

        Thread.sleep(2000);  // Let the user actually see something!

        WebElement grid = driver.findElement(By.id("Grid"));
        Assertions.assertFalse(grid.getText().contains(testProduct.getName()));
    }

    @Test
    public void testCancelButton_formIsEmpty() throws InterruptedException {
        Product testProduct = new Product("testName", "testDesc");
        WebElement updtNameField = driver.findElement(By.id("updtNameField"));
        updtNameField.sendKeys(testProduct.getName());
        WebElement updtDescField = driver.findElement(By.id("updtDescField"));
        updtDescField.sendKeys(testProduct.getDescription());
        WebElement idField = driver.findElement(By.id("idField"));

        WebElement button = driver.findElement(By.xpath("//vaadin-button[contains(.,'Cancel')]"));

        Thread.sleep(1000);  // Let the user actually see something!
        button.click();
        Thread.sleep(1000);  // Let the user actually see something!


        Assertions.assertEquals("", updtNameField.getAttribute("value"));
        Assertions.assertEquals("", updtDescField.getAttribute("value"));
        Assertions.assertEquals("", idField.getAttribute("value"));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
