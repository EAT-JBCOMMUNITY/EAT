package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/messaging/src/main/java","modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8"})
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/UCEWL_SANCTION_REPLY")
    , @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageReader implements MessageListener {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final String P20_SOURCE_SYSTEM = "P20";

    @Inject
    @Booking
    private Event<BookingEvent> bookingEvent;

    @Resource
    ManagedExecutorService threadPool;

    @PersistenceContext
    private EntityManager em;

    public void onMessage(Message message) {
        System.out.println("Received JMS message on P20 input queue");
        if (message instanceof TextMessage) {
            try {
                String content = ((TextMessage) message).getText();
                BookingEvent e = new BookingEvent(content, P20_SOURCE_SYSTEM);
                bookingEvent.fireAsync(e)
                        .exceptionally(ex -> {
                            LOG.error("An error happened while processing async event", ex);
                            return e;
                        });
            } catch (Exception e) {
                LOG.error("An error happened when getting message content {}", e);
                throw new RuntimeException(e);
            }
        } else {
            LOG.error("Incoming message not of the expected type {}, redirect to DLQ", message.getClass().getSimpleName());
        }

        LOG.info("Finished JMS message processing on P20 input queue");
    }

}
