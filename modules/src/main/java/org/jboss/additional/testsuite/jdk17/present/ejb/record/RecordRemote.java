package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import javax.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/Eap7Plus/ejb/src/main/java#7.4.8"})
@Remote
public interface RecordRemote {
    public Record getRecord();
}
