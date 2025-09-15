package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.hal.testsuite.creaper.AddJMSBridge;
import org.jboss.hal.testsuite.fragment.WindowFragment;

/**
 * Abstraction over dialog for adding JMS Bridge in Management Console UI. Only mandatory attributes are covered.
 */
public class AddJMSBridgeDialogFragment extends WindowFragment {

    public AddJMSBridgeDialogFragment name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddJMSBridgeDialogFragment failureRetryInterval(long failureRetryInterval) {
        getEditor().text("failure-retry-interval", String.valueOf(failureRetryInterval));
        return this;
    }

    public AddJMSBridgeDialogFragment maxBatchSize(int maxBatchSize) {
        getEditor().text("max-batch-size", String.valueOf(maxBatchSize));
        return this;
    }

    public AddJMSBridgeDialogFragment maxBatchTime(long maxBatchTime) {
        getEditor().text("max-batch-time", String.valueOf(maxBatchTime));
        return this;
    }

    public AddJMSBridgeDialogFragment maxRetries(int maxRetries) {
        getEditor().text("max-retries", String.valueOf(maxRetries));
        return this;
    }

    public AddJMSBridgeDialogFragment qualityOfService(AddJMSBridge.QualityOfService qualityOfService) {
        getEditor().select("quality-of-service", qualityOfService.getStringValue());
        return this;
    }

    public AddJMSBridgeDialogFragment sourceConnectionFactory(String sourceConnectionFactory) {
        getEditor().text("source-connection-factory", sourceConnectionFactory);
        return this;
    }

    public AddJMSBridgeDialogFragment sourceDestination(String sourceDestination) {
        getEditor().text("source-destination", sourceDestination);
        return this;
    }

    public AddJMSBridgeDialogFragment targetConnectionFactory(String targetConnectionFactory) {
        getEditor().text("target-connection-factory", targetConnectionFactory);
        return this;
    }

    public AddJMSBridgeDialogFragment targetDestination(String targetDestination) {
        getEditor().text("target-destination", targetDestination);
        return this;
    }
}
