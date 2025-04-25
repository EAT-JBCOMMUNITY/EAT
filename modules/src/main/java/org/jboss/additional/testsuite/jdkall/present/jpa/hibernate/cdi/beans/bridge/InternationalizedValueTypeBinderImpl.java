/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.bridge;

import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexFieldReference;
import org.hibernate.search.mapper.pojo.bridge.TypeBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.TypeBindingContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.TypeBridgeWriteContext;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.i18n.InternationalizedValue;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.i18n.Language;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.i18n.LocalizationService;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.model.EntityWithCDIAwareBridges;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jpa/src/main/java#30.0.0"})
@Dependent
public class InternationalizedValueTypeBinderImpl implements InternationalizedValueTypeBinder {

    @Inject
    private LocalizationService localizationService;

    @Override
    public void bind(TypeBindingContext context) {
        context.dependencies().useRootOnly();

        String fieldName = (String) context.param("fieldName");
        IndexFieldReference<String> fieldRef = context.indexSchemaElement()
                .field(fieldName, f -> f.asString())
                .multiValued()
                .toReference();
        context.bridge( EntityWithCDIAwareBridges.class, new Bridge( localizationService, fieldRef ) );
    }

    private static class Bridge implements TypeBridge<EntityWithCDIAwareBridges> {
        private final LocalizationService localizationService;

        private final IndexFieldReference<String> fieldRef;

        private Bridge(LocalizationService localizationService, IndexFieldReference<String> fieldRef) {
            this.localizationService = localizationService;
            this.fieldRef = fieldRef;
        }

        @Override
        public void write(DocumentElement target, EntityWithCDIAwareBridges source, TypeBridgeWriteContext context) {
            InternationalizedValue internationalizedValue = source.getInternationalizedValue();
            for (Language language : Language.values()) {
                String localizedValue = localizationService.localize(internationalizedValue, language);
                target.addValue(fieldRef, localizedValue);
            }
        }
    }

}
