package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import jakarta.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/ejb/src/main/java#28.0.0.Final"})
@Remote
public interface RecordRemote {
    public Record getRecord();
}
