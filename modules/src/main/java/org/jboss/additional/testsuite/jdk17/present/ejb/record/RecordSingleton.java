package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/Eap7Plus/ejb/src/main/java#7.4.8"})
@Startup
@Singleton
public class RecordSingleton {

    private Logger log = Logger.getLogger(RecordSingleton.class.getSimpleName());

    @EJB
    private RecordRemote recordRemote;

    @PostConstruct
    public void init() {
        log.info("Record: " + recordRemote.getRecord());
    }
}
