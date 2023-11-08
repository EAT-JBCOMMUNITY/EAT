package org.jboss.additional.testsuite.jdkall.present.others;

import java.io.IOException;
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
import org.junit.AfterClass;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/others/src/main/java#7.4.14","modules/testcases/jdkAll/WildflyJakarta/others/src/main/java#30.0.0"})
public class CheckLogTestCase {

    private static final String serverLogPath = "target/surefire-reports/org.jboss.additional.testsuite.jdkall.present.others.WarnLogTestCase-output.txt";

    private static final Logger log = LoggerFactory.getLogger(CheckLogTestCase.class);

    
    @Test
    public void warncheck() throws Exception {
                Thread.sleep(5000);
                String path = new File("").getAbsolutePath() + "/" + serverLogPath;
                System.out.println("==== " + path);
		File serverlogfile = new File(path);
                if(serverlogfile.exists()) {
			FileInputStream inputStream = new FileInputStream(path);
			String everything = "";
			try {
			    everything = IOUtils.toString(inputStream); 
			    System.out.println("==== " + everything);
			    Assert.assertFalse(everything.contains("WARNING: An illegal reflective access operation has occurred"));
			} finally {
			    inputStream.close();
			    
			}
		}
        
    }


}
