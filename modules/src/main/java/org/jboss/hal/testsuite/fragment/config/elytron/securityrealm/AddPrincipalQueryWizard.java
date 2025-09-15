package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddPrincipalQueryWizard extends WizardWindowWithOptionalFields {

    public AddPrincipalQueryWizard sql(String sql) {
        getEditor().text("sql", sql);
        return this;
    }

    public AddPrincipalQueryWizard dataSource(String dataSource) {
        getEditor().text("data-source", dataSource);
        return this;
    }

    public AddPrincipalQueryWizard attributeMapping(String... keyValuePairs) {
        maximizeWindow();
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text("attribute-mapping", String.join("\n", keyValuePairs));
        return this;
    }

}
