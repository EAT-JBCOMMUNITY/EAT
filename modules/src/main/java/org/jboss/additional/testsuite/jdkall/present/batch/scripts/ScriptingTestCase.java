package org.jboss.additional.testsuite.jdkall.present.batch.scripts;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.test.integration.management.util.CLITestUtil;
import org.jboss.as.test.shared.TestSuiteEnvironment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 *  Documentation added : https://play.google.com/store/apps/details?id=edu.eatproposals.eatapp ( JBEAP-23792.pdf )
 **/
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/batch/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/batch/src/main/java#28.0.0","modules/testcases/jdkAll/EapJakarta/batch/src/main/java"})
public class ScriptingTestCase {

    public static final String ARCHIVE_NAME = "scripts-test";
    private static final File serverLog = new File(TestSuiteEnvironment.getJBossHome(), "standalone" + File.separator + "log"
            + File.separator + "server.log");
    private CommandContext ctx;

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, ARCHIVE_NAME + ".war");
        return war;
    }

    @Before
    public void setUp() throws Exception {
        ctx = CLITestUtil.getCommandContext();
        ctx.connectController();
    }

    @After
    public void tearDown() throws Exception {
        if (ctx != null) {
            ctx.terminateSession();
        }
    }

    @Test
    public void doTest() throws Exception {
        final ExecutorService executors = Executors.newFixedThreadPool(1);
        final LogMessageListener listener = new LogMessageListener();
        Tailer tailer = new Tailer(serverLog, listener, 1, true);
        Future tailerFuture = null;
        try {
            executors.submit(tailer);
            tailerInitializationWait();
            ctx.handle("/deployment=scripting.war/subsystem=batch-jberet:start-job(job-xml-name=scripting-inline,properties={fail=true})");
            listener.waitForScriptExceptionMsg(10, TimeUnit.SECONDS);
        } finally {
            tailer.stop();
            if (tailerFuture != null) {
                tailerFuture.get(10, TimeUnit.SECONDS);
            }
            executors.shutdown();
        }

        Assert.assertFalse("We did not expect any javascript warning message in server.log!", listener.javascriptExceptionOccurred.get());
    }

    private void tailerInitializationWait() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }
    }

    private static class LogMessageListener extends TailerListenerAdapter {

        final AtomicBoolean javascriptExceptionOccurred = new AtomicBoolean(false);

        final String javascriptExceptionMsg = ".*JBERET000637: Invalid script attributes: type = javascript, src = null.*";

        public void handle(final String line) {
            if (line.matches(javascriptExceptionMsg)) {
                javascriptExceptionOccurred.set(true);
            }
        }

        public void waitForScriptExceptionMsg(int timeout, TimeUnit timeUnit) throws InterruptedException {
            long maxTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);

            while (!javascriptExceptionOccurred.get() && System.currentTimeMillis() < maxTime) {
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }
     
    }
}
