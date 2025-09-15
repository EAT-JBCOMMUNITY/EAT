package org.jboss.hal.testsuite.fragment.config.undertow;

public class AddHTTPSListenerWizard extends AbstractAddHTTPListenerWizard<AddHTTPSListenerWizard> {
    private static final String SSL_CONTEXT = "ssl-context";

    public AddHTTPSListenerWizard sslContext(String value) {
        getEditor().text(SSL_CONTEXT, value);
        return this;
    }
}
