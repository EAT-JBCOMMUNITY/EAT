package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
@ApplicationScoped
public class MessageGenerator {

    @Inject
    //@JMSConnectionFactory("java:/RemoteJms")
    private JMSContext context;

    public Message generateDuplicateMessage(String externalId, String content) {
        return context.createTextMessage(content);
    }

}
