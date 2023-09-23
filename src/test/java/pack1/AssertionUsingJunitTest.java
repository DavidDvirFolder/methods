package pack1;

import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AssertionUsingJunitTest {
    /*
    this is an example for url and title assertion using junit

     */

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    // Assertion that the name of the author displayed on the webpage corresponds to the name included in the URL
    @Test
    public void CheckUrlTitle() {
        String url = "https://github.com/andrejs-ps";
        try {
            driver.get(url);
            wait.until(ExpectedConditions.urlToBe(url));
            WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@class='p-nickname vcard-username d-block' and contains(text(),'andrejs-ps')]")));

            String textName = name.getText(); // -> andrejs-ps
            Assert.assertTrue(driver.getCurrentUrl().contains(textName));
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        //the end
    }
}
