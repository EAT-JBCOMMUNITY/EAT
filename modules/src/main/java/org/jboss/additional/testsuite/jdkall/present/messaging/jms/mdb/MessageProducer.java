package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
@RequestScoped
public class MessageProducer {

    @Resource(lookup = "java:/jms/queue/UCEWL_SANCTION_REPLY")
    private Queue p20out;

    @Inject
    private JMSContext context;

    public void sendToP20Response(Message message) {
        JMSProducer producer = context.createProducer();
        producer.send(p20out, message);
    }

}
