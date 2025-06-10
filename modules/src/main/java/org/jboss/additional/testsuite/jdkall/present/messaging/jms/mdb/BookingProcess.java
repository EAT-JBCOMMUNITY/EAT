package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/messaging/src/main/java","modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
@RequestScoped
@Transactional(value = Transactional.TxType.REQUIRED)
public class BookingProcess {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject
    private MessageProducer producer;

    @Inject
    private MessageGenerator generator;

    public void processBookingRequest(@ObservesAsync @Booking BookingEvent bookingEvent) {
        LOG.info("Processing booking event {} - {}", bookingEvent.getSourceSystem(), bookingEvent.getExternalId());
        producer.sendToP20Response(generator.generateDuplicateMessage("TEST", "CONTENT"));
    }

}
