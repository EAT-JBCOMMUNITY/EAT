package org.jboss.additional.testsuite.jdkall.present.web.jsf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.net.URL;
import java.io.File;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.apache.commons.io.FileUtils;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import java.awt.Desktop;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.7","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#27.0.0.Beta1"})
public class JsfFormOOBIndexTestCase {

    @Deployment(name = "jsf-war", testable = false, order=1)
    public static Archive<?> getJsfWarDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jsf-war.war");
        war.addClass(JsfFormOOBIndexTestCase.class);
        return war;
    }

    @Test
    public void jsfWarTest(@ArquillianResource URL url) throws Exception {
        if(System.getProperty("webdriver.gecko.driver")!=null) {
		String warpath = new File("").getAbsolutePath() + "/src/test/resources/bug/bug.war";
		String deploymentpath = new File("").getAbsolutePath() + "/../../../../../servers/eap7/build/target/jbossas/standalone/deployments/bug.war";

		WebDriver driver = new FirefoxDriver();

		driver.get(url.toURI() + "../bug/index.xhtml");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement button = driver.findElement(By.name("j_idt6:j_idt9"));
		button.click();
		FileUtils.copyFile(new File(warpath), new File(deploymentpath));
		Thread.sleep(1000);
		button = driver.findElement(By.name("j_idt6:j_idt9"));
		button.click();
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		boolean AlertExists=false;
                try
                {
                    w.until(ExpectedConditions.alertIsPresent());
                    AlertExists = true;
                }
                catch(Exception e)
                {
                    AlertExists = false;
                }
                if(AlertExists)
		   fail("Alert should not pop up ...");
		driver.quit();
        } else {
                System.err.println("webdriver.gecko.driver is not set. JsfFormOOBIndexTestCasejsf.WarTest was not executed ...");
        }
    }
}
