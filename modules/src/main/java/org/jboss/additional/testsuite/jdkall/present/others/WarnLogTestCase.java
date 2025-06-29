package org.jboss.additional.testsuite.jdkall.present.others;

import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.util.EntityUtils;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import java.util.concurrent.TimeoutException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/others/src/main/java","modules/testcases/jdkAll/Eap7Plus/others/src/main/java#7.4.14","modules/testcases/jdkAll/WildflyJakarta/others/src/main/java#30.0.0"})
public class WarnLogTestCase {

    private static final String serverLogPath = "target/surefire-reports/org.jboss.additional.testsuite.jdkall.present.others.WarnLogTestCase-output.txt";
    private static final String WARN_DEPLOYMENT = "warn";

    private static final Logger log = LoggerFactory.getLogger(WarnLogTestCase.class);

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, WARN_DEPLOYMENT + ".war");
        log.info(war.toString(true));
        return war;
    }

    
    @Test
    public void startServer(){}


}
