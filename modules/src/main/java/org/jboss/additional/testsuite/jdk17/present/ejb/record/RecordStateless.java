package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@ΕΑΤ({"modules/testcases/jdk17/Eap7Plus/ejb/src/main/java#7.4.8"})
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
