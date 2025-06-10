package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/messaging/src/main/java","modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
public class BookingEvent {

    private String externalId;
    private String sourceSystem;

    public BookingEvent(String externalId, String sourceSystem) {
        this.externalId = externalId;
        this.sourceSystem = sourceSystem;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }
}
