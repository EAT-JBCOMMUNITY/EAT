package org.jboss.additional.testsuite.jdkall.present.web.jsp;

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
import org.jboss.eap.additional.testsuite.annotations.ATTest;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.13","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#31.0.0"})
public class JspIncludeTestCase {

    private final String serverLogPath = "../../../../../servers/wildfly/build/target/jbossas/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/jbossas/standalone/log/server.log";
    private static final String JSP_DEPLOYMENT = "jspinclude";

    private static final Logger log = LoggerFactory.getLogger(JspIncludeTestCase.class);

    private static final String PAGE_CONTENT1 = "<jsp:include page=\"included.jsp?url_param1\">" +
        "<jsp:param name=\"jsp_param1\" value=\"0\" />" +
        "</jsp:include> ";
	
     private static final String PAGE_CONTENT2 = "<jsp:include page=\"included_included.jsp?url_param2\">" 
        + "<jsp:param name=\"jsp_param2\" value=\"0\" />" 
        + "</jsp:include>";
	
    private static final String PAGE_CONTENT3 = "<jsp:include page=\"included_included_included.jsp?url_param3\">" +
        "<jsp:param name=\"jsp_param3\" value=\"0\" />" +
        "</jsp:include>";

     private static final String PAGE_CONTENT4 = "<%" +
         "for (String p : java.util.Collections.list(request.getParameterNames())) {" +
         "System.out.println(p);" +
         "}" +
         "%>";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + ".war");
        war.add(new StringAsset(PAGE_CONTENT1), "main.jsp");
        war.add(new StringAsset(PAGE_CONTENT2), "included.jsp");
        war.add(new StringAsset(PAGE_CONTENT3), "included_included.jsp");
        war.add(new StringAsset(PAGE_CONTENT4), "included_included_included.jsp");
        log.info(war.toString(true));
        return war;
    }

    
    @Test
    public void jspIncludeTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "main.jsp?mainParam");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);
                String content = EntityUtils.toString(response.getEntity());
             //   System.out.println("========== " + content);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);

                String path = new File("").getAbsolutePath() + "/" + serverLogPath;
		File serverlogfile = new File(path);
		if(!serverlogfile.exists()) {
		    path = new File("").getAbsolutePath() + "/" + serverLogPath2;
		}

		FileInputStream inputStream = new FileInputStream(path);
		try {
		    String everything = IOUtils.toString(inputStream); 
		 //   System.out.println(path + "========== " + everything);
		    Assert.assertTrue(everything.contains("mainParam"));
                    Assert.assertTrue(everything.contains("url_param1"));
                    Assert.assertTrue(everything.contains("jsp_param1"));
                    Assert.assertTrue(everything.contains("url_param2"));
                    Assert.assertTrue(everything.contains("jsp_param2"));
                    Assert.assertTrue(everything.contains("url_param3"));
                    Assert.assertTrue(everything.contains("jsp_param3"));           
		} finally {
		    inputStream.close();
		}
                
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }


}
