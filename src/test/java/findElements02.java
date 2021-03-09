import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class findElements02 {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("start-maximized");
        driver = new ChromeDriver(opts);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void findElements() {
        driver.get("");

        WebElement element_manufacturers = driver.findElement(By.cssSelector("li.manufacturers.dropdown"));
        Assertions.assertEquals("Manufacturers", element_manufacturers.getText(), "Element Manufacturers was not found");
        System.out.println("The first element is " + element_manufacturers.getText());

        WebElement element_signin = driver.findElement(By.cssSelector("li.account.dropdown"));
        Assertions.assertEquals("Sign In", element_signin.getText(), "Element SignIn was not found");
        System.out.println("The second element is " + element_signin.getText());

        //WebElement element_second_viewed_product =
        driver.findElement(By.cssSelector("div.product:nth-child(2)")).click();
        //driver.findElement(By.xpath("//*[@id=\"box-recently-viewed-products\"]/div/div[2]")).click();
        //Assertions.assertEquals("Sign In", element_second_viewed_product.getSize(), "Element SignIn was not found");
        //System.out.println("The third element is " + element_second_viewed_product.getTagName());

     }
}
