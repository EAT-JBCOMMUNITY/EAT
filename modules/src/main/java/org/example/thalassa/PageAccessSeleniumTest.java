package org.example.thalassa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/DigitalWorlds/thalassa/src/main/java"})
public class PageAccessSeleniumTest {

    @Test
    void testHtmlBaseThalassaAttributeExists() {
        // Use WebDriverManager to automatically set up the ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the specified URL
            driver.get("https://www.digitalworlds.top/thalassa");

            // Optional: Print the page title to verify a successful navigation
            String pageTitle = driver.getTitle();
            System.out.println("Page Title: " + pageTitle);
            assertTrue(pageTitle.compareTo("app")==0, "Page should contain at least one <title> heading for proper structure.");
            
            // You can add more test steps here, such as finding elements, clicking buttons, etc.
            // For example:
            // WebElement element = driver.findElement(By.id("some-element-id"));
            // element.click();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // It is crucial to close the browser after the test is complete
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
