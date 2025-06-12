package org.jboss.additional.testsuite.jdkall.present.web.jsf;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.5", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#28.0.0"})
public class JsfFormTestCase {

    @Deployment(name = "jsf-form", testable = false)
    public static Archive<?> getJsfFormDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jsf-form.war");
        war.setWebXML("web-jsf.xml");
        war.addClass(TestBean.class);
        war.addAsWebResource("index-jsf.xhtml", "index.xhtml");
        war.addAsWebResource("templates/header.xhtml", "templates/header.xhtml");
        war.addAsWebResource("templates/layout.xhtml", "templates/layout.xhtml");
        return war;
    }

    @Test
    public void jsfFormTest(@ArquillianResource URL url) throws Exception {

        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page1 = webClient.getPage(url.toURI() + "/index.xhtml");

            HtmlForm form = page1.getFormByName("it");

            HtmlSubmitInput button = form.getInputByName("it:test01");

            HtmlPage page2 = button.click();

            form = page2.getFormByName("it");

            button = form.getInputByName("it:test01");

            page2 = button.click();

            page2 = button.click();

            form = page2.getFormByName("it");

            button = form.getInputByName("it:test01");

            page2 = button.click();

            page2 = button.click();

            form = page2.getFormByName("it");

            button = form.getInputByName("it:test01");

            page2 = button.click();

            Assert.assertTrue("The jsf form should contain the enctype info ...", page2.getBody().asXml().contains("enctype="));

        }

    }
}
