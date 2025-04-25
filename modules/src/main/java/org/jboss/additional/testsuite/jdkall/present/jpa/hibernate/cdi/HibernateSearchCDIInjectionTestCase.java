/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.CDIBeansPackage;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.i18n.InternationalizedValue;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.model.EntityWithCDIAwareBridges;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.cdi.beans.model.EntityWithCDIAwareBridgesDao;
import org.jboss.as.test.shared.util.AssumeTestGroupUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceDescriptor;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.inject.Inject;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jpa/src/main/java#30.0.0"})
@RunWith(Arquillian.class)
public class HibernateSearchCDIInjectionTestCase {

    @BeforeClass
    public static void securityManagerNotSupportedInHibernateSearch() {
        AssumeTestGroupUtil.assumeSecurityManagerDisabled();
    }

    @Deployment
    public static Archive<?> createTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, HibernateSearchCDIInjectionTestCase.class.getSimpleName() + ".war")
                .addClass(HibernateSearchCDIInjectionTestCase.class)
                .addPackages(true /* recursive */, CDIBeansPackage.class.getPackage())
                .addAsResource(persistenceXml(), "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static Asset persistenceXml() {
        String persistenceXml = Descriptors.create(PersistenceDescriptor.class)
                .version("2.0")
                .createPersistenceUnit()
                .name("primary")
                .jtaDataSource("java:jboss/datasources/ExampleDS")
                .getOrCreateProperties()
                .createProperty().name("hibernate.hbm2ddl.auto").value("create-drop").up()
                .createProperty().name("hibernate.search.schema_management.strategy").value("drop-and-create-and-drop").up()
                .createProperty().name("hibernate.search.backend.type").value("lucene").up()
                .createProperty().name("hibernate.search.backend.lucene_version").value("LUCENE_CURRENT").up()
                .createProperty().name("hibernate.search.backend.directory.type").value("local-heap").up()
                .up().up()
                .exportAsString();
        return new StringAsset(persistenceXml);
    }

    @Inject
    private EntityWithCDIAwareBridgesDao dao;

    @Before
    @After
    public void cleanupDatabase() {
        dao.deleteAll();
    }

    @Test
    public void injectedFieldBridge() {
        assertEquals(0, dao.searchValueBridge("bonjour").size());
        assertEquals(0, dao.searchValueBridge("hello").size());
        assertEquals(0, dao.searchValueBridge("hallo").size());
        assertEquals(0, dao.searchValueBridge("au revoir").size());

        EntityWithCDIAwareBridges entity = new EntityWithCDIAwareBridges();
        entity.setInternationalizedValue(InternationalizedValue.HELLO);
        dao.create(entity);
        assertThat(dao.searchValueBridge("bonjour"), hasItems(entity.getId()));
        assertThat(dao.searchValueBridge("hello"), hasItems(entity.getId()));
        assertThat(dao.searchValueBridge("hallo"), hasItems(entity.getId()));
        assertEquals(0, dao.searchValueBridge("au revoir").size());

        EntityWithCDIAwareBridges entity2 = new EntityWithCDIAwareBridges();
        entity2.setInternationalizedValue(InternationalizedValue.GOODBYE);
        dao.create(entity2);
        assertThat(dao.searchValueBridge("bonjour"), hasItems(entity.getId()));
        assertThat(dao.searchValueBridge("hello"), hasItems(entity.getId()));
        assertThat(dao.searchValueBridge("hallo"), hasItems(entity.getId()));
        assertThat(dao.searchValueBridge("au revoir"), hasItems(entity2.getId()));

        dao.delete(entity);
        assertEquals(0, dao.searchValueBridge("bonjour").size());
        assertEquals(0, dao.searchValueBridge("hello").size());
        assertEquals(0, dao.searchValueBridge("hallo").size());
        assertThat(dao.searchValueBridge("au revoir"), hasItems(entity2.getId()));
    }

    @Test
    public void injectedClassBridge() {
        assertEquals(0, dao.searchTypeBridge("bonjour").size());
        assertEquals(0, dao.searchTypeBridge("hello").size());
        assertEquals(0, dao.searchTypeBridge("hallo").size());
        assertEquals(0, dao.searchTypeBridge("au revoir").size());

        EntityWithCDIAwareBridges entity = new EntityWithCDIAwareBridges();
        entity.setInternationalizedValue(InternationalizedValue.HELLO);
        dao.create(entity);
        assertThat(dao.searchTypeBridge("bonjour"), hasItems(entity.getId()));
        assertThat(dao.searchTypeBridge("hello"), hasItems(entity.getId()));
        assertThat(dao.searchTypeBridge("hallo"), hasItems(entity.getId()));
        assertEquals(0, dao.searchTypeBridge("au revoir").size());

        EntityWithCDIAwareBridges entity2 = new EntityWithCDIAwareBridges();
        entity2.setInternationalizedValue(InternationalizedValue.GOODBYE);
        dao.create(entity2);
        assertThat(dao.searchTypeBridge("bonjour"), hasItems(entity.getId()));
        assertThat(dao.searchTypeBridge("hello"), hasItems(entity.getId()));
        assertThat(dao.searchTypeBridge("hallo"), hasItems(entity.getId()));
        assertThat(dao.searchTypeBridge("au revoir"), hasItems(entity2.getId()));

        dao.delete(entity);
        assertEquals(0, dao.searchTypeBridge("bonjour").size());
        assertEquals(0, dao.searchTypeBridge("hello").size());
        assertEquals(0, dao.searchTypeBridge("hallo").size());
        assertThat(dao.searchTypeBridge("au revoir"), hasItems(entity2.getId()));
    }
}
