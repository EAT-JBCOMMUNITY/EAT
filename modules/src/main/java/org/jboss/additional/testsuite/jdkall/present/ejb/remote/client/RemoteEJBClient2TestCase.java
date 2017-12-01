package org.jboss.additional.testsuite.jdkall.present.ejb.remote.client;

import javax.naming.NamingException;
import org.jboss.additional.testsuite.jdkall.present.ejb.remote.stateless.HelloBean;
import org.jboss.additional.testsuite.jdkall.present.ejb.remote.stateless.HelloBeanRemote;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.test.integration.domain.management.util.DomainTestSupport;
import org.jboss.as.test.integration.management.base.AbstractCliTestBase;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java"})
public class RemoteEJBClient2TestCase extends AbstractCliTestBase {

    private static final String ARCHIVE_NAME = "test";
    private static String result = null;

    @Deployment
    public static Archive<?> deploy() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
        jar.addClass(RemoteEJBClient2TestCase.class);
        jar.addPackage(HelloBeanRemote.class.getPackage());
        return jar;
    }
    
    @BeforeClass
    public static void before() throws Exception {
        AbstractCliTestBase.initCLI(DomainTestSupport.masterAddress);
    }

    @AfterClass
    public static void after() throws Exception {
        AbstractCliTestBase.closeCLI();
    }

    @Test 
    public void testServerSuspentionMode() throws Exception {
        try {
            Thread t = new Thread(){
                public void run(){
                    try {
                         Process p = Runtime.getRuntime().exec("ps -ef");
                        // Invoke a stateless bean
                        final HelloBeanRemote statelessHelloBeanRemote = lookupRemoteStatelessBean();
                        result = statelessHelloBeanRemote.hello();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            t.start();
            
            Thread.sleep(5000);
            // Suspending the server
            cli.sendLine(":suspend(timeout=12)");
            System.out.println("=============== " + result);
            assertTrue("The bean execution should have finished.",result!=null);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Looks up and returns the proxy to remote stateless calculator bean
     *
     * @return
     * @throws NamingException
     */
    private static HelloBeanRemote lookupRemoteStatelessBean() throws Exception {
        EJBDirectory context = new RemoteEJBDirectory(ARCHIVE_NAME,true);
        HelloBeanRemote bean = context.lookupStateless(HelloBean.class, HelloBeanRemote.class);
 
        return bean;
    }

}
