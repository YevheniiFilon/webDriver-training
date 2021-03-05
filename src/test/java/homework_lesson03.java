import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class homework_lesson03 {
    WebDriver driver;
    String user_login = "testadmin";
    String user_pass = "R8MRDAYT_test";
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("start-maximized");
        driver = new ChromeDriver(opts);
        driver.get("http://158.101.173.161/admin/");

        wait = new WebDriverWait(driver,10);

        WebElement login_field = driver.findElement(By.name("username"));
        login_field.clear();
        login_field.click();
        login_field.sendKeys(user_login);

        WebElement pass_field = driver.findElement(By.name("password"));
        pass_field.clear();
        pass_field.click();
        pass_field.sendKeys(user_pass);

        WebElement login_btn = driver.findElement(By.cssSelector("button.btn.btn-default"));
        login_btn.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.nav-toggle")));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void menuItems(){
        //get number of all items of the main menu
        int count_menu_items = driver.findElements(By.xpath("//*[@id='box-apps-menu']/li")).size();

        for (int i=1; i<=count_menu_items; i++){
            WebElement menu_item = driver.findElement(By.xpath("//*[@id='box-apps-menu']/li["+i+"]/a"));
            //click on the item of the main menu
            menu_item.click();
            //try to find sub menu of the previous item
            if (isElementPresent(driver, By.cssSelector("ul.docs"))){
                //get the number of sub menus
                int count_submenu = driver.findElements(By.xpath("//*[@id='box-apps-menu']//ul[@class='docs']/li")).size();
                    for (int a=1; a<=count_submenu; a++){
                        //find the sub menu item and click on it
                        WebElement submenu_item = driver.findElement(By.xpath("//*[@id='box-apps-menu']//ul[@class='docs']/li["+a+"]/a"));
                        wait.until(ExpectedConditions.elementToBeClickable(submenu_item));
                        submenu_item.click();
                        WebElement panel_heading = driver.findElement(By.xpath("//*[@id='content']//div[@class='panel-heading']"));
                        panel_heading.isDisplayed();
                        System.out.println(panel_heading.getText());
                        driver.navigate().refresh();
                    }
            } else {
                WebElement panel_heading = driver.findElement(By.xpath("//*[@id='content']//div[@class='panel-heading']"));
                panel_heading.isDisplayed();
                System.out.println(panel_heading.getText());
                driver.navigate().refresh();
                //continue;
            }
        }
    }



    public boolean isElementPresent(WebDriver driver, By locator)
    { return  driver.findElements(locator).size()>0; }


}
