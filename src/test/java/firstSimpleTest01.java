import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class firstSimpleTest01 {

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
        void verifyGoogleSearchWorks() {
            driver.get("http://google.com");

            driver.findElement(By.name("q")).sendKeys("Selenium" + Keys.ENTER);
            Assertions.assertEquals("Selenium WebDriver", driver.findElement(By.cssSelector("h3")).getText(), "Text on site is differ than expected");
        }

}
