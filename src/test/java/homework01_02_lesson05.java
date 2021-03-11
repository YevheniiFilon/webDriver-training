import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class homework01_02_lesson05 {
    EventFiringWebDriver edr;
    WebDriverWait wait;
    String user_login = "";
    String user_pass = "";
    String url = "";

    @BeforeEach
    public void start() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("start-maximized");
        edr = new EventFiringWebDriver(new ChromeDriver(opts));
        edr.register(new Listener());
        wait = new WebDriverWait(edr, 10);
    }

    @AfterEach
    public void stop() {
        edr.quit();
    }

    @Test
    public void part2(){
        performLogin();

        edr.get("http://158.101.173.161/admin/?app=countries&doc=countries");
        //find an AddNewCountry button
        WebElement addNewCountry = edr.findElement(By.xpath("//a[@class='btn btn-default']"));
        //click on it
        addNewCountry.click();
        wait.until(ExpectedConditions.elementToBeClickable(edr.findElement(By.xpath("//div[@class='btn-group btn-block btn-group-inline']"))));
        //for each item with an arrow, click on it and wait until a new window opens
        for (WebElement linkArrow : edr.findElements(By.xpath("//i[@class='fa fa-external-link']"))) {
            String originalW = edr.getWindowHandle();            //get the id of the main window
            Set<String> existWs = edr.getWindowHandles();        //create a set for existing windows
            linkArrow.click();                                      //click on the element with arrow
            String newW = wait.until(anyWindowOtherThan(existWs));  //wait until the id of the new window appears in the set
            edr.switchTo().window(newW);                         //switch to the new window
            System.out.println(edr.getTitle());                //get the title of this page to make sure you are on the right page
            edr.close();                                         //close the new window
            edr.switchTo().window(originalW);                    //come back to the main window
        }


    }



    private void performLogin() {
        By sidebar = By.id("box-apps-menu");

        edr.navigate().to(url +"/admin/");

        if (isElementPresent(sidebar)) return;
        edr.findElement(By.name("username")).sendKeys(user_login);
        edr.findElement(By.name("password")).sendKeys(user_pass);
        edr.findElement(By.cssSelector("button[name=login]")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(sidebar));
    }

    private boolean isElementPresent(By element) {
        return edr.findElements(element).size() > 0;
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> windows) {
        return input -> {
            Set<String> handles = edr.getWindowHandles();
            handles.removeAll( windows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }
}
