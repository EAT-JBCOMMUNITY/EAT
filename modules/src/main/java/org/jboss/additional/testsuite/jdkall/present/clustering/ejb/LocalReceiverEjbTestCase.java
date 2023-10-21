package org.jboss.additional.testsuite.jdkall.present.clustering.ejb;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertTrue;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/clustering/src/main/java#7.4.14","modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#32.0.0"})
public class LocalReceiverEjbTestCase {

    private static final File serverLog = new File(System.getProperty("jboss.dist"), "standalone" + File.separator + "log"
            + File.separator + "server.log");

    @Deployment(name="dep1")
    @TargetsContainer("container-2")
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "LocalReceiverEjbTestCase.war");
        return war;
    }

    @Deployment(name="dep2")
    @TargetsContainer("container-3")
    public static WebArchive getDeployment2() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "LocalReceiverEjbTestCase.war");
        return war;
    }


    @Test
    public void testLocalReceiver() throws Exception {
        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page1 = webClient.getPage("http://localhost:8080/jsf-ejb/index.jsf");

            HtmlForm form = page1.getFormByName("reg");

            HtmlSubmitInput button = form.getInputByName("reg:j_idt15");

            HtmlPage page2 = button.click();

            form = page2.getFormByName("reg");

            button = form.getInputByName("reg:j_idt15");

            page2 = button.click();

            form = page2.getFormByName("reg");

            button = form.getInputByName("reg:j_idt15");

            page2 = button.click();
            
            form = page2.getFormByName("reg");

            button = form.getInputByName("reg:j_idt15");

            page2 = button.click();
	}

        FileInputStream inputStream = new FileInputStream(serverLog.getAbsolutePath());
        try {
            String everything = IOUtils.toString(inputStream);
            assertTrue("JBEAP24059 fails ...", !everything.contains("EJBAccessException: WFLYEJB0364"));
        } finally {
            inputStream.close();
        }
    }
}
