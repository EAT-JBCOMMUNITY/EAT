package org.jboss.hal.testsuite.util;

import static org.wildfly.extras.creaper.core.online.Constants.SUBSYSTEM;

import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.jboss.hal.testsuite.creaper.ResourceVerifier;
import org.jboss.hal.testsuite.dmr.ModelNodeGenerator;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.extras.creaper.core.online.Constants;
import org.wildfly.extras.creaper.core.online.ModelNodeResult;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;
import org.wildfly.extras.creaper.core.online.operations.Values;
import org.wildfly.extras.creaper.core.online.operations.admin.DomainAdministration;

import java.io.IOException;

/**
 * Class which serves as an abstraction over modifying and verifying credential-reference attributes throughout HAL
 */
public class ElytronIntegrationChecker {

    private static final Logger log = LoggerFactory.getLogger(ElytronIntegrationChecker.class);

    private static final String
            CREDENTIAL_REFERENCE = "credential-reference",
            CREDENTIAL_STORE = "credential-store",
            CLEAR_TEXT = "clear-text",
            SECRET_VALUE = "secret-value",
            ADD_ALIAS = "add-alias",
            ALIAS = "alias",
            STORE = "store",
            TYPE = "type",
            ELYTRON = "elytron";

    private final OnlineManagementClient client;
    private final Operations operations;
    private ConfigFragment configFragment;
    private WizardWindow wizardWindow;
    private Address address;
    private String credentialReferenceAttributeName;

    private ElytronIntegrationChecker(Builder builder) {
        this.client = builder.client;
        this.operations = new Operations(client);
        this.address = builder.address;
        this.configFragment = builder.configFragment;
        this.wizardWindow = builder.wizardWindow;
        this.credentialReferenceAttributeName = builder.credentialReferenceAttributeName;
    }

    /**
     * Sets value to clear-text field, saves form and verifies value of credential-reference against model
     *
     * @param errorMessage error message to use for example to describe known issue
     */
    public void setClearTextCredentialReferenceAndVerify(String errorMessage) throws Exception {
        final String clearTextValue = "clear-text-value_" + RandomStringUtils.randomAlphanumeric(6);
        final ModelNodeResult originalValue = operations.readAttribute(address, credentialReferenceAttributeName);
        originalValue.assertSuccess();
        try {
            final ResourceVerifier verifier = new ConfigChecker.Builder(client, address)
                    .configFragment(configFragment)
                    .wizardWindow(wizardWindow)
                    .edit(ConfigChecker.InputType.TEXT, ALIAS, "")
                    .edit(ConfigChecker.InputType.TEXT, CLEAR_TEXT, clearTextValue)
                    .edit(ConfigChecker.InputType.TEXT, STORE, "")
                    .edit(ConfigChecker.InputType.TEXT, TYPE, "")
                    .andSave()
                    .verifyFormSaved(errorMessage);

            final CredentialStoreModelNodeBuilder builder = new CredentialStoreModelNodeBuilder()
                    .clearText(clearTextValue);

            try { //workaround for https://issues.jboss.org/browse/HAL-1292
                verifier.verifyAttribute(credentialReferenceAttributeName, builder.build(), errorMessage);
            } catch (AssertionError e) {
                log.warn("Attribute has probably defined explicit undefined values, trying verifying with explicitly" +
                        "defined undefined values! See https://issues.jboss.org/browse/HAL-1292.", e);
                verifier.verifyAttribute(credentialReferenceAttributeName, builder.writeUndefinedValuesExplicitly().build(), errorMessage);
            }

        } finally {
            operations.writeAttribute(address, credentialReferenceAttributeName, originalValue.value());
        }
    }

    /**
     * Sets value to clear-text field, saves form and verifies value of credential-reference against model
     */
    public void setClearTextCredentialReferenceAndVerify() throws Exception {
        setClearTextCredentialReferenceAndVerify("");
    }

    /**
     * Sets value to 'store' and 'alias' fields, saves form and verifies value of defined attribute name against model
     *
     * @param errorMessage error message to use for example to describe known issue
     */
    public void setCredentialStoreCredentialReferenceAndVerify(String errorMessage) throws Exception {
        final String credentialStoreName = "credential-store_" + RandomStringUtils.randomAlphanumeric(6),
                credentialStoreAliasName = "credential-store-alias_"
                        + RandomStringUtils.randomAlphanumeric(6).toLowerCase(),
                credentialStoreAliasValue = "alias-value_" + RandomStringUtils.randomAlphanumeric(6);
        final ModelNodeResult originalValue = operations.readAttribute(address, credentialReferenceAttributeName);
        originalValue.assertSuccess();
        //add credential store
        final Address credentialReferenceAddress = addCredentialStore(credentialStoreName);
        //add alias with value to credential store
        addAlias(credentialStoreAliasValue, credentialReferenceAddress);
        //edit form in web console and verify against model
        try {
            final ResourceVerifier verifier = new ConfigChecker.Builder(client, address)
                    .configFragment(configFragment)
                    .wizardWindow(wizardWindow)
                    .edit(ConfigChecker.InputType.TEXT, ALIAS, credentialStoreAliasName, ConfigChecker.InputMethod.HUMAN)
                    .edit(ConfigChecker.InputType.TEXT, CLEAR_TEXT, "")
                    .edit(ConfigChecker.InputType.TEXT, STORE, credentialStoreName)
                    .edit(ConfigChecker.InputType.TEXT, TYPE, "")
                    .andSave()
                    .verifyFormSaved(errorMessage);

            final CredentialStoreModelNodeBuilder builder = new CredentialStoreModelNodeBuilder()
                    .aliasName(credentialStoreAliasName)
                    .storeName(credentialStoreName);

            try { //workaround for https://issues.jboss.org/browse/HAL-1292
                verifier.verifyAttribute(credentialReferenceAttributeName, builder.build(), errorMessage);
            } catch (AssertionError e) {
                log.warn("Attribute has probably defined explicit undefined values, trying verifying with explicitly" +
                        "defined undefined values! See https://issues.jboss.org/browse/HAL-1292.", e);
                verifier.verifyAttribute(credentialReferenceAttributeName, builder.writeUndefinedValuesExplicitly().build(), errorMessage);
            }
        } finally {
            //revert to original
            operations.writeAttribute(address, credentialReferenceAttributeName, originalValue.value());
            operations.removeIfExists(credentialReferenceAddress);
        }
    }

    /**
     * Sets value to 'store' and 'alias' fields, saves form and verifies value of defined attribute name against model
     */
    public void setCredentialStoreCredentialReferenceAndVerify() throws Exception {
        setCredentialStoreCredentialReferenceAndVerify("");
    }

    /**
     * Tests illegal combination of attributes for defined credential reference attribute name
     */
    public void testIllegalCombinationCredentialReferenceAttributes(String errorMessage) throws Exception {
        final ModelNodeResult originalValue = operations.readAttribute(address, credentialReferenceAttributeName);
        originalValue.assertSuccess();
        final String credentialStoreName = "credential-store-name_" + RandomStringUtils.randomAlphanumeric(6);
        //add credential store (in case capabilities restriction will be in place)
        final Address credentialStoreAddress = addCredentialStore(credentialStoreName);
        try {
            new ConfigChecker.Builder(client, address)
                    .configFragment(configFragment)
                    .wizardWindow(wizardWindow)
                    .edit(ConfigChecker.InputType.TEXT, ALIAS, "")
                    .edit(ConfigChecker.InputType.TEXT, CLEAR_TEXT, RandomStringUtils.randomAlphanumeric(6))
                    .edit(ConfigChecker.InputType.TEXT, STORE, credentialStoreName)
                    .edit(ConfigChecker.InputType.TEXT, TYPE, "")
                    .andSave()
                    .verifyFormNotSaved(errorMessage);
        } finally {
            operations.writeAttribute(address, credentialReferenceAttributeName, originalValue.value());
            operations.removeIfExists(credentialStoreAddress);
        }
    }

    /**
     * Tests illegal combination of attributes for defined credential reference attribute name
     */
    public void testIllegalCombinationCredentialReferenceAttributes() throws Exception {
        testIllegalCombinationCredentialReferenceAttributes("");
    }

    private Address addCredentialStore(String credentialStoreName) throws IOException {
        final Address credentialReferenceAddress = Address.subsystem(ELYTRON).and(CREDENTIAL_STORE, credentialStoreName);
        operations.add(credentialReferenceAddress, Values
                .of("create", true)
                .and(CREDENTIAL_REFERENCE, new ModelNode().set(new Property(CLEAR_TEXT, new ModelNode("foobar")))
                        .asObject()))
                .assertSuccess();
        return credentialReferenceAddress;
    }

    private void addAlias(final String credentialStoreAliasValue, final Address credentialReferenceAddress)
            throws IOException {
        if (ConfigUtils.isDomain()) {
            for (String server : new DomainAdministration(client).allRunningServers()) {
                Address serverCredentialReferenceAddress = Address.host(ConfigUtils.getDefaultHost())
                        .and(Constants.SERVER, server).and(SUBSYSTEM, ELYTRON)
                        .and(CREDENTIAL_STORE, credentialReferenceAddress.getLastPairValue());
                invokeAddAlias(credentialStoreAliasValue, serverCredentialReferenceAddress);
            }
        } else {
            invokeAddAlias(credentialStoreAliasValue, credentialReferenceAddress);
        }
    }

    private void invokeAddAlias(final String credentialStoreAliasValue, final Address credentialReferenceAddress)
            throws IOException {
        operations.invoke(ADD_ALIAS, credentialReferenceAddress, Values.empty()
                .and(ALIAS, credentialStoreAliasValue)
                .and(SECRET_VALUE, credentialStoreAliasValue)).assertSuccess();
    }

    private static final class CredentialStoreModelNodeBuilder {

        private boolean writeUndefinedValuesExplicitly;
        private String clearText;
        private String storeName;
        private String aliasName;
        private String typeName;

        public CredentialStoreModelNodeBuilder writeUndefinedValuesExplicitly() {
            this.writeUndefinedValuesExplicitly = true;
            return this;
        }

        public CredentialStoreModelNodeBuilder clearText (String clearText) {
            this.clearText = clearText;
            return this;
        }

        public CredentialStoreModelNodeBuilder storeName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public CredentialStoreModelNodeBuilder aliasName(String aliasName) {
            this.aliasName = aliasName;
            return this;
        }

        public CredentialStoreModelNodeBuilder typeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public ModelNode build() {
            ModelNodeGenerator.ModelNodePropertiesBuilder builder = new ModelNodeGenerator.ModelNodePropertiesBuilder();
            if (aliasName != null) {
                builder.addProperty(ALIAS, aliasName);
            } else if (writeUndefinedValuesExplicitly) {
                builder.addProperty(ALIAS, new ModelNode());
            }
            if (clearText != null) {
                builder.addProperty(CLEAR_TEXT, clearText);
            } else if (writeUndefinedValuesExplicitly) {
                builder.addProperty(CLEAR_TEXT, new ModelNode());
            }
            if (storeName != null) {
                builder.addProperty(STORE, storeName);
            } else if (writeUndefinedValuesExplicitly) {
                builder.addProperty(STORE, new ModelNode());
            }
            if (typeName != null) {
                builder.addProperty(TYPE, typeName);
            } else if (writeUndefinedValuesExplicitly) {
                builder.addProperty(TYPE, new ModelNode());
            }
            return builder.build();
        }
    }

    public static final class Builder {

        private final OnlineManagementClient client;
        private String credentialReferenceAttributeName;
        private Address address;
        private ConfigFragment configFragment;
        private WizardWindow wizardWindow;

        public Builder(OnlineManagementClient client) {
            this.client = client;
        }

        /**
         * Name of credential reference attributes when it differs from standard 'credential-reference' name.
         */
        public Builder credetialReferenceAttributeName(String credentialReferenceAttributeName) {
            this.credentialReferenceAttributeName = credentialReferenceAttributeName;
            return this;
        }

        /**
         * Address where the attribute containing credential-reference value is located
         */
        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        /**
         * Config fragment containing credential-reference input fields
         */
        public Builder configFragment(ConfigFragment configFragment) {
            this.configFragment = configFragment;
            return this;
        }

        /**
         * Wizard window for use cases when config fragment is inside wizard window which is closed upon clicking on
         * save button. If wizard window remains open, do not use this setter.
         * @param wizardWindow wizard window containing config fragment
         */
        public Builder wizardWindow(WizardWindow wizardWindow) {
            this.wizardWindow = wizardWindow;
            return this;
        }


        private void validate() {
            if (client == null) {
                throw new IllegalStateException("Client cannot be null!");
            }
            if (configFragment == null) {
                throw new IllegalStateException("Config fragment cannot be null!");
            }
            if (address == null) {
                throw new IllegalStateException("Address cannot be null!");
            }
            if (credentialReferenceAttributeName == null) {
                credentialReferenceAttributeName = CREDENTIAL_REFERENCE;
            }
        }

        public ElytronIntegrationChecker build() {
            validate();
            return new ElytronIntegrationChecker(this);
        }

    }

}
