package org.jboss.additional.testsuite.jdkall.present.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Test;
import org.junit.runner.RunWith;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/selenium/src/main/java"})
@RunWith(Arquillian.class)
@RunAsClient
public class ServerAccessSeleniumTest {

    @Drone
    private WebDriver driver;

    static {
        WebDriverManager.chromedriver().setup();
    }
    
    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(ServerAccessSeleniumTest.class);
        archive.addPackages(true, "org.openqa.selenium");
        return archive;
    }

    @Test
    public void testHtmlServerTitleAttributeExists() {
        
        try {
            // Navigate to the specified URL
            driver.get("http://localhost:8080");

            // Optional: Print the page title to verify a successful navigation
            String pageTitle = driver.getTitle();
            System.out.println("Page Title: " + pageTitle);
            assertTrue("Page should contain at least one <title> heading for proper structure.", pageTitle.compareTo("Welcome to WildFly")==0);
            
           
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

  //  @Test
    public void testHtmlManagementTitleAttributeExists() {
        
        try {
            // Navigate to the specified URL
            driver.get("http://localhost:9990/console/index.html");

            // Optional: Print the page title to verify a successful navigation
            String pageTitle = driver.getTitle();
            System.out.println("Page Title: " + pageTitle);
            assertTrue("Page should contain at least one <title> heading for proper structure.", pageTitle.compareTo("HAL Management Console")==0);
            
           
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
