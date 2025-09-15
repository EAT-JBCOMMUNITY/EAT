package org.jboss.hal.testsuite.creaper;

import org.wildfly.extras.creaper.core.online.OnlineCommand;
import org.wildfly.extras.creaper.core.online.OnlineCommandContext;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;
import org.wildfly.extras.creaper.core.online.operations.Values;

/**
 * Creaper command for adding JMS Bridge. Only mandatory attributes are covered.
 * @see <a href="https://docs.jboss.org/author/display/WFLY8/Messaging+configuration#Messagingconfiguration-JMSBridge">https://docs.jboss.org/author/display/WFLY8/Messaging+configuration#Messagingconfiguration-JMSBridge</a>
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 9/19/16.
 */
public final class AddJMSBridge implements OnlineCommand {

    private String name;
    private boolean replaceExisting;
    private QualityOfService qualityOfService;
    private int failureRetryInterval;
    private int maxRetries;
    private int maxBatchSize;
    private int maxBatchTime;
    private String sourceConnectionFactory;
    private String sourceDestination;
    private Values sourceContext;
    private String targetConnectionFactory;
    private String targetDestination;
    private Values targetContext;

    private AddJMSBridge(Builder builder) {
        this.name = builder.name;
        this.replaceExisting = builder.replaceExisting;
        this.qualityOfService = builder.qualityOfService;
        this.failureRetryInterval = builder.failureRetryInterval;
        this.maxRetries = builder.maxRetries;
        this.maxBatchSize = builder.maxBatchSize;
        this.maxBatchTime = builder.maxBatchTime;
        this.sourceConnectionFactory = builder.sourceConnectionFactory;
        this.sourceDestination = builder.sourceDestination;
        this.sourceContext = builder.sourceContext;
        this.targetConnectionFactory = builder.targetConnectionFactory;
        this.targetDestination = builder.targetDestination;
        this.targetContext = builder.targetContext;
    }

    @Override
    public void apply(OnlineCommandContext ctx) throws Exception {
        Address address = Address.subsystem("messaging-activemq").and("jms-bridge", name);
        Operations operations = new Operations(ctx.client);

        if (replaceExisting) {
            operations.removeIfExists(address);
        }

        Values params = Values.of("quality-of-service", qualityOfService.getStringValue())
                .and("failure-retry-interval", failureRetryInterval)
                .and("max-retries", maxRetries)
                .and("max-batch-size", maxBatchSize)
                .and("max-batch-time", maxBatchTime)
                .and("source-connection-factory", sourceConnectionFactory)
                .and("source-destination", sourceDestination)
                .and("target-connection-factory", targetConnectionFactory)
                .and("target-destination", targetDestination);
        if (sourceContext != null) {
            params.andObject("source-context", sourceContext);
        }
        if (targetContext != null) {
            params.andObject("target-context", targetContext);
        }

        operations.add(address, params);
    }

    @Override
    public String toString() {
        return "Command for adding JMS Bridge named '" + name + "\' with attributes: {" +
                "replaceExisting=" + replaceExisting +
                ", qualityOfService=" + qualityOfService +
                ", failureRetryInterval=" + failureRetryInterval +
                ", maxRetries=" + maxRetries +
                ", maxBatchSize=" + maxBatchSize +
                ", maxBatchTime=" + maxBatchTime +
                ", sourceConnectionFactory='" + sourceConnectionFactory + '\'' +
                ", sourceDestination='" + sourceDestination + '\'' +
                ", sourceContext=" + sourceContext +
                ", targetConnectionFactory='" + targetConnectionFactory + '\'' +
                ", targetDestination='" + targetDestination + '\'' +
                ", targetContext=" + targetContext +
                '}';
    }

    /**
     * Enum containing possible values for quality of service for JMS Bridge
     * @see <a href="https://activemq.apache.org/artemis/docs/1.0.0/jms-bridge.html#quality-of-service">https://activemq.apache.org/artemis/docs/1.0.0/jms-bridge.html#quality-of-service</a>
     */
    public enum QualityOfService {
        AT_MOST_ONCE("AT_MOST_ONCE"),
        DUPLICATES_OK("DUPLICATES_OK"),
        ONCE_AND_ONLY_ONCE("ONCE_AND_ONLY_ONCE");

        private String stringValue;

        QualityOfService(String stringValue) {
            this.stringValue = stringValue;
        }

        public String getStringValue() {
            return stringValue;
        }
    }

    public static final class Builder {

        private String name;
        private boolean replaceExisting;
        private QualityOfService qualityOfService;
        private int failureRetryInterval;
        private int maxRetries;
        private int maxBatchSize;
        private int maxBatchTime;
        private String sourceConnectionFactory;
        private String sourceDestination;
        private Values sourceContext;
        private String targetConnectionFactory;
        private String targetDestination;
        private Values targetContext;

        /**
         * @param name desired name of JMS bridge
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * The desired quality of service mode (AT_MOST_ONCE, DUPLICATES_OK or ONCE_AND_ONLY_ONCE).
         * @param qualityOfService quality of service mode
         */
        public Builder qualityOfService(QualityOfService qualityOfService) {
            this.qualityOfService = qualityOfService;
            return this;
        }

        /**
         * The amount of time in milliseconds to wait between trying to recreate connections to the source or target
         * servers when the bridge has detected they have failed.
         * @param failureRetryInterval amount of time in milliseconds
         */
        public Builder failureRetryInterval(int failureRetryInterval) {
            this.failureRetryInterval = failureRetryInterval;
            return this;
        }

        /**
         * The number of times to attempt to recreate connections to the source or target servers when the bridge has
         * detected they have failed. The bridge will give up after trying this number of times. -1 represents
         * 'try forever'.
         * @param maxRetries number of times to attempt to recreate connections
         */
        public Builder maxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        /**
         * The maximum number of messages to consume from the source destination before sending them in a batch to the
         * target destination. Its value must >= 1.
         * @param maxBatchSize maximum number of messages to consume from the source destination
         */
        public Builder maxBatchSize(int maxBatchSize) {
            this.maxBatchSize = maxBatchSize;
            return this;
        }

        /**
         * The maximum number of milliseconds to wait before sending a batch to target, even if the number of messages
         * consumed has not reached max-batch-size. Its value must be -1 to represent 'wait forever', or >= 1 to specify
         * an actual time.
         * @param maxBatchTime maximum number of milliseconds to wait
         */
        public Builder maxBatchTime(int maxBatchTime) {
            this.maxBatchTime = maxBatchTime;
            return this;
        }

        /**
         * The name of the source connection factory to lookup on the source messaging server.
         * @param sourceConnectionFactory name of the source connection factory
         */
        public Builder sourceConnectionFactory(String sourceConnectionFactory) {
            this.sourceConnectionFactory = sourceConnectionFactory;
            return this;
        }

        /**
         * The name of the source destination to lookup on the source messaging server.
         * @param sourceDestination name of the source destination
         */
        public Builder sourceDestination(String sourceDestination) {
            this.sourceDestination = sourceDestination;
            return this;
        }

        /**
         * The properties used to configure the source JNDI initial context.
         * @param sourceContext properties used to configure JNDI context
         */
        public Builder sourceContext(Values sourceContext) {
            this.sourceContext = sourceContext;
            return this;
        }

        /**
         * The name of the target connection factory to lookup on the target messaging server.
         * @param targetConnectionFactory name of the target connection factory
         */
        public Builder targetConnectionFactory(String targetConnectionFactory) {
            this.targetConnectionFactory = targetConnectionFactory;
            return this;
        }

        /**
         * The name of the target destination to lookup on the target messaging server.
         * @param targetDestination name of the target destination
         */
        public Builder targetDestination(String targetDestination) {
            this.targetDestination = targetDestination;
            return this;
        }

        /**
         * The properties used to configure the target JNDI initial context.
         * @param targetContext properties used to configure JNDI context
         */
        public Builder targetContext(Values targetContext) {
            this.targetContext = targetContext;
            return this;
        }

        /**
         * Determines whether newly created JMS bridge should replace existing with same name.
         */
        public Builder replaceExisting() {
            this.replaceExisting = true;
            return this;
        }

        public AddJMSBridge build() {
            return new AddJMSBridge(this);
        }

    }
}
