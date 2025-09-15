package org.jboss.hal.testsuite.fragment.config.elytron.securityrealmmapper;

import org.jboss.dmr.ModelNode;

import java.util.Map;
import java.util.stream.Collectors;

public class AddMappedRegexRealmMapperWizard extends AbstractAddRealmMapperWizard<AddMappedRegexRealmMapperWizard> {

    public AddMappedRegexRealmMapperWizard pattern(String pattern) {
        getEditor().text("pattern", pattern);
        return this;
    }

    public AddMappedRegexRealmMapperWizard realmMap(Map<String, ModelNode> values) {
        getEditor().text("realm-map", values.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue().asString())
                .collect(Collectors.joining("\n")));
        return this;
    }
}
