package org.jboss.additional.testsuite.jdkall.present.web.js;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import java.io.File;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.11", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
public class HttpJsWebTestCase {

    private static final String DEPLOYMENT = "httpjs.war";

    private static final String JAR_STATIC_INDEX_CONTENT = "<html>\n"
            + "<head>\n"
            + "<title>MomentJS - Working Example</title>\n"
            + "<script type = \"text/JavaScript\" src = \"moment.js\"></script>\n"
            + "</head>\n"
            + "<body>\n"
            + "<h1>Sample TLD</h1>\n"
            + "<script type = \"text/JavaScript\">\n"
            + "moment().locale(\"en\")\n"
            + "alert(moment().locale(\"./../../en\").format('LLLL')); \n"
            + "</script>\n"
            + "</body>\n"
            + "</html>";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getDeployment() throws Exception {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT)
                .add(new UrlAsset(HttpJsWebTestCase.class.getResource("moment.js")), "moment.js")
                .add(new StringAsset(JAR_STATIC_INDEX_CONTENT), "index.html");
    //    war.as(ZipExporter.class).exportTo(new File("/home/psotirop/httpjs.war"), true);

        return war;
    }

    @Test
    public void momentLocalTest(@ArquillianResource URL url) throws IOException, URISyntaxException, ScriptException, NoSuchMethodException {
        try (final WebClient webClient = new WebClient()) {

            final List collectedAlerts = new ArrayList();
            webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

            webClient.getPage(url.toURI() + "/index.html");

            final String expectedAlerts = "./../../en";
            Assert.assertTrue(collectedAlerts.contains(expectedAlerts));
            final HtmlPage page2 = webClient.getPage("https://MomentJS.com/downloads/moment.js");
            Assert.assertTrue(page2.getBody().asXml().contains("isLocaleNameSane"));
        }
    }

}
