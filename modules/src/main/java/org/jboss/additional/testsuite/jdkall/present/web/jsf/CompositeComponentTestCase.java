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
@EAT({"modules/testcases/jdkAll/EapJakarta/web/src/main/java#8.0.9", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#36.0.0"})
public class CompositeComponentTestCase {

    private static String getXhtml() {
        return "<ui:component\n" +
	    "xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
		"xmlns:h=\"jakarta.faces.html\"\n" +
		"xmlns:ui=\"jakarta.faces.facelets\"\n" +
	    "xmlns:cc=\"jakarta.faces.composite\"\n" +
		"xmlns:f=\"jakarta.faces.core\">\n" +
    "<cc:interface componentType=\"customComponent\">\n" +
    "<cc:interface componentType=\"customComponent\">\n" +
		"<cc:attribute name=\"customRole\" required=\"false\" default=\"user\" type=\"java.lang.String\" />\n" +
    "</cc:interface>\n" +
    "<cc:implementation>\n" +
        "<h:outputText value=\"Role\"/>\n" +
		"<h:commandButton action=\"#{cc.action}\" value=\"Action\"/>\n" +
    "</cc:implementation>\n" +
"</ui:component>";
    }


    @Deployment(name = "jsf-compositecomponent", testable = false)
    public static Archive<?> getJsfDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jsf-compositecomponent.war");
        war.addClass(CustomComponent.class);
        war.addAsWebResource(new StringAsset(getXhtml()), "index.xhtml");
        war.addAsWebResource(new StringAsset(""), "faces-config.xml");
        return war;
    }

    @Test
    public void jsfTest(@ArquillianResource URL url) throws Exception {

        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page = webClient.getPage(url.toURI() + "/index.xhtml");
System.out.println(page.getBody().asXml());
      //      Assert.assertTrue("The jsf form should contain the enctype info ...", page2.getBody().asXml().contains("enctype="));

        }

    }
}
