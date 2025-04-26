package org.jboss.additional.testsuite.jdkall.present.ejb.client.outbound.connection;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
//@AT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
public class TooManyChannelsTestCase {

    private final String serverLogPath = "../../../../../servers/wildfly/build/target/jbossas/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/jbossas/standalone/log/server.log";

    @Test
    public void tooManyChannelsTest() throws Exception {
	for(int i=1; i<=41; i++) {
      		HttpClient client = new DefaultHttpClient();
  		HttpGet request = new HttpGet("http://localhost:8080/web-"+ i + "/channeltest?nr=" + i);
  		client.execute(request);
  	}

	String path = new File("").getAbsolutePath() + "/" + serverLogPath;
	File serverlogfile = new File(path);
        if(!serverlogfile.exists()) {
            path = new File("").getAbsolutePath() + "/" + serverLogPath2;
        }
        FileInputStream inputStream = new FileInputStream(path);
        try {
            String everything = IOUtils.toString(inputStream);
            assertTrue("JBEAP27435 fails ...", !everything.contains("Too many channels open"));
        } finally {
            inputStream.close();
        }
    }
}
