package com.example.myproject;

import java.time.Duration;
import java.util.List;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RepositoryTest {

    // Constants for maintenance
    // Refactor to Junit5 will speed up the execution time

    static WebDriver driver;
    static WebDriverWait wait;
    private static final String BaseUrl = "https://github.com/DavidDvirFolder";
    private static final String RepoUrl = BaseUrl + "?tab=repositories";
    private int numberOfRepositories;


    @BeforeEach
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }
    @AfterEach
    public void cleanUp(){
        if (driver!=null){
            driver.quit();
        }
    }
    // Assert that the username displayed at the webpage match to the username included in the URL
    @Test
    public void CheckUrlTitle() {
        String url = "https://github.com/DavidDvirFolder";
            driver.get(url);
            wait.until(ExpectedConditions.urlToBe(url));
            WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@class='p-nickname vcard-username d-block' and contains(text(),'DavidDvirFolder')]")));

            String textName = user.getText(); // -> DavidDvirFolder
            Assertions.assertTrue(driver.getCurrentUrl().contains(textName), "Username in URL doesn't match displayed username");
        //the end
    }
    @Test
    public void repoCount() {
        driver.get(RepoUrl);
        // Wait for the repositories to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.wb-break-all")));
        List<WebElement> repositories = driver.findElements(By.cssSelector("h3.wb-break-all"));
        int repositoriesSize = repositories.size();
        WebElement repositoriesCount = driver.findElement(By.xpath("//span[@class='Counter']"));
        String repoText = repositoriesCount.getText();
        try {
            numberOfRepositories = Integer.parseInt(repoText.replaceAll(",", "")); // in case the value > 1000
        } catch (NumberFormatException e) {
            System.err.println("Error parsing text to integer");
        }
        Assertions.assertEquals(repositoriesSize, numberOfRepositories, "Number of repositories doesn't match");
    }
}