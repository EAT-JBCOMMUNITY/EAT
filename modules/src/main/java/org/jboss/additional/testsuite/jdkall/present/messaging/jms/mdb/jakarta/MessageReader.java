package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import javax.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.event.Event;
import javax.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/UCEWL_SANCTION_REPLY")
    , @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
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
