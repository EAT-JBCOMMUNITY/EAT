package org.jboss.additional.testsuite.jdkall.present.selenium.afterlogin;

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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;


//@AT({"modules/testcases/jdkAll/WildflyJakarta/selenium/src/main/java"})
@RunWith(Arquillian.class)
@RunAsClient
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsoleAccessSeleniumTest {

    @Drone
    private WebDriver driver;

    static {
        WebDriverManager.chromedriver().setup();
    }
    
    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(ConsoleAccessSeleniumTest.class);
        archive.addPackages(true, "org.openqa.selenium");
        return archive;
    }

    @Test
    public void test1HtmlConsoleAccessDeploymentPage() {
        
        try {
            
            // Navigate to the specified URL
            driver.get("http://test:test@localhost:9990/console/index.html#deployments");

            String pageSource = driver.getPageSource();
            System.out.println("PageSource: " + pageSource);
            assertTrue("Page should contain the deployment page text.", pageSource.contains("A deployment represents anything that can be deployed (e.g. an application such as EJB-JAR, WAR, EAR, any kind of standard archive such as RAR or JBoss-specific deployment) into a server."));
            
           
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // It is crucial to close the browser after the test is complete
            // The last test will close the driver
            if (driver != null) {
                driver.quit();
            }
        }
    }

}
