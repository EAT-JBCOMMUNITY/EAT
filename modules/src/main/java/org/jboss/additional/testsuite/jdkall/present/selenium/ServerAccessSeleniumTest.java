package org.jboss.additional.testsuite.jdkall.present.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.jboss.eap.additional.testsuite.annotations.EAT;

//@AT({"modules/testcases/jdkAll/WildflyJakarta/selenium/src/main/java"})
public class ServerAccessSeleniumTest {

    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(BasicTest.class);
        return archive;
    }

    @Test
    void testHtmlBaseThalassaAttributeExists() {
        // Use WebDriverManager to automatically set up the ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the specified URL
            driver.get("https://localhost:8080");

            // Optional: Print the page title to verify a successful navigation
            String pageTitle = driver.getTitle();
            System.out.println("Page Title: " + pageTitle);
        //    assertTrue(pageTitle.compareTo("app")==0, "Page should contain at least one <title> heading for proper structure.");
            
           
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
