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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class homework_lesson04 {
    WebDriver driver;
    WebDriverWait wait;
    double rand = Math.random();


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("start-maximized");
        driver = new ChromeDriver(opts);
        driver.get("http://158.101.173.161/");
        wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("box-popular-products")));
        isElementPresent(By.id("box-popular-products"));

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void findElements() {
        By acceptCookies = By.xpath("//div[@id='page']//button[@name='accept_cookies']");
        if (isElementPresent(acceptCookies)){
            driver.findElement(acceptCookies).click();
        }

        int overallNumberItems = 3;
        int itemInCart = 1;
        while (itemInCart <= overallNumberItems) {
            //find all of the popular products and count them
            List<WebElement> popularProducts = driver.findElements(By.xpath("//*[@id='box-popular-products']//div[@class='listing products']/article[@class='product-column']"));
            int numberPopularProducts = popularProducts.size();
            //get the random item from popular products and click on it
            int rand = randomNumber(numberPopularProducts);
            popularProducts.get(rand - 1).click();
            //add this item to the cart
            WebElement addToCartBtn = driver.findElement(By.name("add_cart_product"));
            wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
            Actions addToCart = new Actions(driver).moveToElement(addToCartBtn);
            addToCart.click().perform();
            //wait for adding this item to the cart
            WebElement numberCartItems = driver.findElement(By.xpath("//div[@class='badge quantity']"));
            String strItems = String.valueOf(itemInCart);
            wait.until(ExpectedConditions.textToBePresentInElement(numberCartItems,strItems));
            //back to the main page
            WebElement backToMainPage = driver.findElement(By.cssSelector("a.logotype"));
            backToMainPage.click();
            itemInCart++;
        }

        //find the cart element
        WebElement cart = driver.findElement(By.id("cart"));
        cart.click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("box-checkout"))));

        //delete items until the cart is empty
        while (!driver.findElement(By.id("content")).getText().contains("There are no items in your cart.")){
            driver.findElement(By.name("remove_cart_item")).click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//section[@id='box-checkout-shipping']/h2")),"Shipping"));
            pause(1);
        }

    }

    boolean isElementPresent(By element) {
        return driver.findElements(element).size() > 0;
    }
    void pause(int sec)
    {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static int randomNumber(int maxRange){
        int min = 1;
        int range = (maxRange - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
