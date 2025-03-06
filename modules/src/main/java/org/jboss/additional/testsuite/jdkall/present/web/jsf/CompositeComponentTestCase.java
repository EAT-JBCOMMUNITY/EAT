package org.jboss.additional.testsuite.jdkall.present.web.jsf;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.net.URL;
import org.jboss.shrinkwrap.api.asset.StringAsset;

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
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.21","modules/testcases/jdkAll/EapJakarta/web/src/main/java#8.0.9", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#36.0.0"})
public class CompositeComponentTestCase {

    @Deployment(name = "jsf-compositecomponent", testable = false)
    public static Archive<?> getJsfDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jsf-compositecomponent.war");
        war.addClass(CompositeComponentTestCase.class);
        return war;
    }

    @Test
    public void jsfTest() throws Exception {

        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page = webClient.getPage("http://localhost:8080/InjectTest/index.xhtml");
System.out.println(page.getBody().asXml());
            Assert.assertTrue("The jsf form should contain the user attribute string ...", page.getBody().asXml().contains("user"));

        }

    }
}
