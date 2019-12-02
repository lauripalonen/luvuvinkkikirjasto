package lukuvinkkikirjasto;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * Test class to explore Selenium tests.
 * 
 * @author hanihani
 */
public class Tester {

    public static void main(String[] args) {
        //WebDriver driver = new ChromeDriver();

        WebDriver driver = new FirefoxDriver();
        final String URL = "http://localhost:3001";
        driver.get(URL);

        driver.get("http://localhost:3001");

        sleep(2);

        // testi: uuden linkin lisääminen
        WebElement element = driver.findElement(By.linkText("Lisää uusi muistiinpano"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("header"));
        element.sendKeys("Google");
        element = driver.findElement(By.name("url"));
        element.sendKeys("www.google.com");
        element = driver.findElement(By.name("add"));

        sleep(2);
        element.submit();

        sleep(2);
        driver.quit();
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
