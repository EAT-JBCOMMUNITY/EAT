package org.jboss.additional.testsuite.jdkall.present.ejb.transactional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author <a href="mailto:tadamski@redhat.com">Tomasz Adamski</a>
 */
@EAT({"modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.15"})
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class BMTSLSB {

    @PostConstruct
    void onConstruct() {
        try {
            this.checkUserTransactionAccess();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkUserTransactionAvailability() {
        try {
            this.checkUserTransactionAccess();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    private void checkUserTransactionAccess() throws NamingException {
        final String remoteUserTransactionName = "txn:RemoteUserTransaction";
        final UserTransaction remoteUserTransaction = InitialContext.doLookup(remoteUserTransactionName);
        if (remoteUserTransaction == null) {
            throw new RuntimeException("UserTransaction lookup at " + remoteUserTransactionName + " returned null in a BMT bean");
        }
        final String localUserTransactionName = "txn:LocalUserTransaction";
        final UserTransaction localUserTransaction = InitialContext.doLookup(localUserTransactionName);
        if (localUserTransaction == null) {
            throw new RuntimeException("UserTransaction lookup at " + localUserTransaction + " returned null in a BMT bean");
        }
    }
}
