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
@EAT({"modules/testcases/jdkAll/Eap73x/others/src/main/java","modules/testcases/jdkAll/Eap7Plus/others/src/main/java#7.4.10","modules/testcases/jdkAll/EapJakarta/others/src/main/java","modules/testcases/jdkAll/WildflyJakarta/others/src/main/java#32.0.0.Final"})
public class ExpressionFactoryCommentTestCase {

    private final String serverLogPath = "../../../../../servers/wildfly/build/target/jbossas/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/jbossas/standalone/log/server.log";
    private static final String DEPLOYMENT = "expressionFactoryCommentTestCase";

    private static final Logger log = LoggerFactory.getLogger(ExpressionFactoryCommentTestCase.class);

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT + ".war");
	war.addAsResource("WEB-INF/classes/META-INF/services/javax.el.ExpressionFactory");
        return war;
    }

    
    @Test
    public void expressionFactoryCommentTest() throws Exception {
        Thread.sleep(5000);
                String path = new File("").getAbsolutePath() + "/" + serverLogPath;
		File serverlogfile = new File(path);
		if(!serverlogfile.exists()) {
		    path = new File("").getAbsolutePath() + "/" + serverLogPath2;
		}

		FileInputStream inputStream = new FileInputStream(path);
		try {
		    String everything = "";
		    everything = IOUtils.toString(inputStream); 
	 	    System.out.println("==== " + everything);
		    Assert.assertFalse(everything.contains("# testing comment1 not found"));
		} finally {
		    inputStream.close();
		}
                
    }


}
