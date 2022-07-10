package org.jboss.additional.testsuite.jdkall.present.web.ajp;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import org.apache.commons.io.input.Tailer;
import org.jboss.additional.testsuite.jdkall.present.shared.ServerLogPatternListener;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Wildfly/web/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.4"})
public class AjpTestCase {

    private final static String WARNAME = "AjpTestCase.war";
    private static final File SERVER_LOG = new File(getJBossHome(), "standalone" + File.separator + "log"
            + File.separator + "server.log");

    @Deployment(name = "war")
    public static Archive<?> createWar() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, WARNAME);
        war.addClasses(AjpTestCase.class);
        return war;
    }

    @Test
    public void checkTimeoutTest() throws IOException, TimeoutException, InterruptedException,
            ExecutionException {

        String hostName = "localhost";
        int port = 8009;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        ServerLogPatternListener listener = new ServerLogPatternListener(Pattern.compile(".*ERROR.*"));
        Tailer serverLogTailer = new Tailer(SERVER_LOG, listener, 100, true);
        Thread tailerThread = new Thread(serverLogTailer);

        try {
            tailerThread.start();
            try {
                socket.connect(socketAddress, timeout);
                Thread.sleep(5000);
                socket.close();

            } catch (Exception exception) {
                exception.printStackTrace();
                fail("No exception expected...");
            }

            Assert.assertTrue("Server log contains error messages caused by resource definitions!", listener.getMatchedLines().isEmpty());
        } finally {
            tailerThread.stop();
        }

    }

    private static String getJBossHome() {
        String jbossHome = System.getProperty("jboss.dist");
	if (jbossHome == null) {
	    jbossHome = System.getProperty("jboss.home");
	}
	return jbossHome == null ? System.getenv("JBOSS_HOME") : jbossHome;
    }

}
