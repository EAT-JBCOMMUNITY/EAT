package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/ejb/src/main/java#28.0.0.Final"})
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
