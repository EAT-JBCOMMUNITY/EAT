package org.jboss.additional.testsuite.jdkall.present.web.js;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk11/Eap7Plus/web/src/main/java#7.4.5","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/web/src/main/java","modules/testcases/jdkAll/Wildfly/web/src/main/java#27.0.0"})
public class JsTestCase {

    @Test
    public void jsMomentTest() throws Exception {

        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page = webClient.getPage("https://MomentJS.com/downloads/moment.js");

            Assert.assertTrue(page.getBody().asXml().contains("getLocale"));
            Assert.assertTrue(page.getBody().asXml().contains("isLocaleNameSane"));
            Assert.assertTrue(page.getBody().asXml().contains("loadLocale"));
        }

    }
}
