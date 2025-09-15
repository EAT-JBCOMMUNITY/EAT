package org.jboss.hal.testsuite.fragment.config.elytron.authentication;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddMatchRuleWizard extends WizardWindow {

    private static final String
            AUTHENTICATION_CONFIGURATION = "authentication-configuration",
            SSL_CONTEXT = "ssl-context",
            MATCH_ABSTRACT_TYPE = "match-abstract-type",
            MATCH_ABSTRACT_TYPE_AUTHORITY = "match-abstract-type-authority",
            MATCH_HOST = "match-host",
            MATCH_LOCAL_SECURITY_DOMAIN = "match-local-security-domain",
            MATCH_NO_USER = "match-no-user",
            MATCH_PATH = "match-path",
            MATCH_PORT = "match-port",
            MATCH_PROTOCOL = "match-protocol",
            MATCH_URN = "match-urn",
            MATCH_USER = "match-user";

    public AddMatchRuleWizard matchAbstractType(String abstractType) {
        getEditor().text(MATCH_ABSTRACT_TYPE, abstractType);
        return this;
    }

    public AddMatchRuleWizard matchAbstractTypeAuthority(String abstractAuthority) {
        getEditor().text(MATCH_ABSTRACT_TYPE_AUTHORITY, abstractAuthority);
        return this;
    }

    public AddMatchRuleWizard matchHost(String host) {
        getEditor().text(MATCH_HOST, host);
        return this;
    }

    public AddMatchRuleWizard matchLocalSecurityDomain(String localSecurityDomain) {
        getEditor().text(MATCH_LOCAL_SECURITY_DOMAIN, localSecurityDomain);
        return this;
    }

    public AddMatchRuleWizard matchNoUser(boolean matchNoUser) {
        getEditor().checkbox(MATCH_NO_USER, matchNoUser);
        return this;
    }

    public AddMatchRuleWizard matchPath(String path) {
        getEditor().text(MATCH_PATH, path);
        return this;
    }

    public AddMatchRuleWizard matchPort(long port) {
        getEditor().text(MATCH_PORT, String.valueOf(port));
        return this;
    }

    public AddMatchRuleWizard matchProtocol(String protocol) {
        getEditor().text(MATCH_PROTOCOL, protocol);
        return this;
    }

    public AddMatchRuleWizard matchUrn(String urn) {
        getEditor().text(MATCH_URN, urn);
        return this;
    }

    public AddMatchRuleWizard matchUser(String user) {
        getEditor().text(MATCH_USER, user);
        return this;
    }

    public AddMatchRuleWizard authenticationConfiguration(String conf) {
        getEditor().text(AUTHENTICATION_CONFIGURATION, conf);
        return this;
    }

    public AddMatchRuleWizard sslContext(String sslContext) {
        getEditor().text(SSL_CONTEXT, sslContext);
        return this;
    }
}
