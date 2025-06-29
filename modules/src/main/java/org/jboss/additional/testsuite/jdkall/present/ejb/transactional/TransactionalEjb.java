package org.jboss.additional.testsuite.jdkall.present.ejb.transactional;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.8"})
@Stateless
public class TransactionalEjb {

    @TransactionAttribute(NOT_SUPPORTED)
    public boolean notSupportedTransactional() throws Exception {
        boolean success = false;
        Properties props = new Properties();
        props.put("java.naming.factory.initial", "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put("java.naming.provider.url", "remote://localhost:4447");
        props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        InitialContext ctx = new InitialContext(props);
        UserTransaction tx = (UserTransaction) ctx.lookup("txn:LocalUserTransaction");
        tx.begin();
        success = true;
        tx.commit();
        return success;
    }

    @TransactionAttribute(REQUIRED)
    public boolean requiredTransactional() throws Exception {
        boolean success = false;
        Properties props = new Properties();
        props.put("java.naming.factory.initial", "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put("java.naming.provider.url", "remote://localhost:4447");
        props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        InitialContext ctx = new InitialContext(props);
        UserTransaction tx = (UserTransaction) ctx.lookup("txn:LocalUserTransaction");
        tx.begin();
        tx.commit();
        success = true;
        return success;
    }
}
