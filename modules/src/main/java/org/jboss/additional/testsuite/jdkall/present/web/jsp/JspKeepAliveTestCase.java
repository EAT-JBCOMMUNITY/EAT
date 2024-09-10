package org.jboss.additional.testsuite.jdkall.present.web.jsp;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#35.0.0","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.20"})
public class JspKeepAliveTestCase {

    private static final String JSP_DEPLOYMENT = "examplejsp";

    private static final String PAGE_CONTENT = "<% \n" +
	"System.out.println(\"examplejsp.jsp - start\"); \n" +
	"int size = 16 * 1024; \n" +
	"char[] buffer = new char[size]; \n" +
	"for (int i = 0; i < size; i++) { \n" +
	"    buffer[i] = 'X'; \n" +
	"} \n" +
	"try { \n" +
	"    System.out.println(\"sleep 10 seconds\"); \n" +
	"    Thread.sleep(10*1000L); \n" +
	"} catch (Exception ignore) {} \n" +
	"out.print(\"start\"); \n" +
	"out.write(buffer); \n" +
	"out.print(\"end\"); \n" +
	"System.out.println(\"examplejsp.jsp - end\"); \n" +
	"%>";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + ".war");
        war.add(new StringAsset(PAGE_CONTENT), "examplejsp.jsp");
        return war;
    }

    @Test
    public void testKeepAliveJsp() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("./curlexec.sh").redirectErrorStream(true);
        pb.redirectError(new File("output.txt"));
        pb.start();
        
        Thread.sleep(5000);
        
        String content = new String(Files.readAllBytes(Paths.get("output.txt")), StandardCharsets.UTF_8);
        assertTrue(content.contains("end"));
    }

  
}
