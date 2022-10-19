package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import java.util.logging.Logger;

import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/ejb/src/main/java#28.0.0.Final"})
@Stateless
public class RecordStateless implements RecordRemote {

    private Logger log = Logger.getLogger(RecordStateless.class.getSimpleName());

    @Override
    public Record getRecord() {
        Record record = new Record("test", "this is a test");
        log.info("Returning record: " + record);
        return record;
    }
}
