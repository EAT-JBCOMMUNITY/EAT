package org.jboss.additional.testsuite.jdkall.present.elytron.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.MyBean;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RunAsPrincipalEJB;
import org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication.RunAsPrincipalEJBRemote;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import javax.ejb.EJB;
import org.jboss.arquillian.junit.Arquillian;

@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0.Alpha1*27.0.0.Alpha3", "modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.3"})
@RunWith(Arquillian.class)
public class RunAsTestCase {

    private static final String NAME = RunAsTestCase.class.getName();
    private static final String EJB_NAME = NAME + ".jar";

    @EJB
    RunAsPrincipalEJBRemote runAsPrincipal;

    @Deployment
    public static JavaArchive deploymentEjb() {
        final JavaArchive ejb = ShrinkWrap.create(JavaArchive.class, EJB_NAME)
                .addClasses(RunAsPrincipalEJB.class, RunAsPrincipalEJBRemote.class, MyBean.class);
        return ejb;
    }

    @Test
    public void testRunAs() throws Exception {
        assertTrue(runAsPrincipal.getInfo().compareTo("anonymous") == 0);
    }

}
