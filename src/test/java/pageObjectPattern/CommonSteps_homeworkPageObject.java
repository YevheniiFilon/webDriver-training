package pageObjectPattern;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class CommonSteps_homeworkPageObject {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "box-popular-products")
    WebElement popularProducts;

    @FindBy(name = "add_cart_product")
    WebElement btnAddToCart;

    @FindBy(xpath = "//div[@class='badge quantity']")
    WebElement numberCartItems;

    @FindBy(id = "cart")
    WebElement cart;

    @FindBy(css = "a.logotype")
    WebElement backToMainPage;

    @FindBy(name = "remove_cart_item")
    WebElement removeItemBtn;

    @FindBy(id = "box-checkout")
    WebElement tableItemsInCart;

    String BASE_URL = "http://158.101.173.161";
    Random random = new Random();



    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        wait = new WebDriverWait(driver,10);
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.elementToBeClickable(popularProducts));


        By acceptCookies = By.name("accept_cookies");
        if (isElementPresent(acceptCookies)){
            driver.findElement(acceptCookies).click();
        }

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    boolean isElementPresent(By element) {
        return driver.findElements(element).size() > 0;
    }

    void openCart(){
        cart.click();
        wait.until(ExpectedConditions.elementToBeClickable(tableItemsInCart));
    }

    void cleanUpCart(){
        while (!driver.findElement(By.id("content")).getText().contains("There are no items in your cart.")){
            removeItemBtn.click();
            //wait.until(ExpectedConditions.stalenessOf(tableItemsInCart));
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//section[@id='box-checkout-shipping']/h2")),"Shipping"));
            pause(1);
        }
    }

    void clickOnPopularProduct(){
        List<WebElement> ListPopularProducts = driver.findElements(By.xpath("//*[@id='box-popular-products']//div[@class='listing products']/article[@class='product-column']"));
        int numberPoProducts = ListPopularProducts.size();
        //get the random item from popular products and click on it
        ListPopularProducts.get(random.nextInt(numberPoProducts)+1).click();
    }

    void addPopularProductToCart(){
        wait.until(ExpectedConditions.elementToBeClickable(btnAddToCart));
        Actions addToCart = new Actions(driver).moveToElement(btnAddToCart);
        addToCart.pause(500).click().perform();
    }

    public void addItemsToCart(int overallItems){
        for (int itemInCart = 1; itemInCart <=overallItems; itemInCart++){
            clickOnPopularProduct();
            addPopularProductToCart();
            String strItems = String.valueOf(itemInCart);
            wait.until(ExpectedConditions.textToBePresentInElement(numberCartItems,strItems));
            backMainPage();
        }
    }

    void backMainPage(){
        backToMainPage.click();
    }


    void pause(int sec)
    {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


