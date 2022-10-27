package org.jboss.additional.testsuite.jdkall.present.elytron.ejb;

import java.util.Hashtable;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.MyBean;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RunAsPrincipalEJB;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RunAsEJBRemote;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RunAsEJB;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RunAsPrincipalEJBRemote;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RolePrincipalPairValidityEJB;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RolePrincipalPairValidityEJBRemote;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.StringAsset;

@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0.Alpha2*27.0.0.Alpha3", "modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.5"})
@RunWith(Arquillian.class)
public class RunAsTestCase {

    private static final String NAME = RunAsTestCase.class.getName();
    private static final String EJB_NAME = NAME + ".jar";

    private static final String CONFIG = "<configuration>\n"
            + "    <authentication-client xmlns=\"urn:elytron:1.0\">\n"
            + "        <authentication-rules>\n"
            + "            <rule use-configuration=\"default-config\"/>\n"
            + "        </authentication-rules>\n"
            + "        <authentication-configurations>\n"
            + "            <configuration name=\"default-config\">\n"
            + "                <sasl-mechanism-selector selector=\"DIGEST-MD5\"/>\n"
            + "                <providers>\n"
            + "                    <use-service-loader />\n"
            + "                </providers>\n"
            + "            </configuration>\n"
            + "        </authentication-configurations>\n"
            + "    </authentication-client>\n"
            + "</configuration>";

    static boolean fail = false;

    @EJB
    RunAsPrincipalEJBRemote runAsPrincipal;

    @EJB
    RunAsEJBRemote runAs;

    @Deployment
    public static JavaArchive deploymentEjb() {
        final JavaArchive ejb = ShrinkWrap.create(JavaArchive.class, EJB_NAME)
                .addClasses(RunAsPrincipalEJB.class, RunAsPrincipalEJBRemote.class, RunAsEJBRemote.class, RunAsEJB.class, MyBean.class, RolePrincipalPairValidityEJB.class, RolePrincipalPairValidityEJBRemote.class)
                .addAsResource(new StringAsset(CONFIG), "wildfly-config.xml");
        return ejb;
    }

    @ATTest({"modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.9"})
    public void testRunAsPrincipal() throws Exception {
        assertTrue(runAsPrincipal.getInfo().compareTo("user2") == 0);
    }

    @Test
    public void testRunAs() throws Exception {
        try {
            runAs.getInfo();
            Assert.fail();
        } catch (Exception e) {
            //this is expected
        }
    }

    @Test
    public void testCompatibilityRunAs() throws Exception {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");

        for (int i = 0; i < 1000; i++) {
            new Thread() {
                public void run() {
                    if (Math.random() > 0.5) {
                        jndiProperties.put(Context.SECURITY_PRINCIPAL, "admin");
                        jndiProperties.put(Context.SECURITY_CREDENTIALS, "admin");
                    } else {
                        jndiProperties.put(Context.SECURITY_PRINCIPAL, "user");
                        jndiProperties.put(Context.SECURITY_CREDENTIALS, "user");
                    }

                    try {

                        final Context context = new InitialContext(jndiProperties);

                        RolePrincipalPairValidityEJBRemote reference = (RolePrincipalPairValidityEJBRemote) context.lookup("ejb:/test/RolePrincipalPairValidityEJB!org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RolePrincipalPairValidityEJBRemote");
                        if (!reference.getRolePrincipalPairValidity()) {
                            fail = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        Thread.sleep(10000);

        if (fail) {
            Assert.fail("Detected incompatible principal and role pair ...");
        }

    }

}
