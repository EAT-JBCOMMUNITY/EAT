package org.jboss.additional.testsuite.jdkall.present.basic;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.common.HttpRequest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATFeature;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/Wildfly/basic/src/main/java#17.0.0.Final","modules/testcases/jdkAll/ServerBeta/basic/src/main/java#17.0.0.Final","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/basic/src/main/java"})
public class JspTestCase {

    private static final String DEPLOYMENT = "jsp-test.war";

    @Deployment(name = DEPLOYMENT)
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT);
        war.addAsWebResource("jsp-test.jsp");
        return war;
    }


    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void testJsp(@ArquillianResource URL url) throws TimeoutException, ExecutionException, IOException {
        String response = HttpRequest.get(url + "jsp-test.jsp", 10, TimeUnit.SECONDS);
        Assert.assertEquals("Jsp doesn't contain valid output", "1.0", response.trim());
    }
}
