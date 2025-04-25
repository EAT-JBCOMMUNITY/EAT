/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.backend.elasticsearch.extension;

import com.google.gson.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.search.backend.elasticsearch.ElasticsearchExtension;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.api.annotation.Observer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.backend.elasticsearch.util.ElasticsearchServerSetupObserver;
import org.jboss.as.test.shared.util.AssumeTestGroupUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.spec.se.manifest.ManifestDescriptor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jpa/src/main/java#30.0.0"})
@RunWith(Arquillian.class)
@Observer(ElasticsearchServerSetupObserver.class)
public class HibernateSearchElasticsearchGsonDependencyTestCase {

    @BeforeClass
    public static void testRequiresDocker() {
        AssumeTestGroupUtil.assumeDockerAvailable();
    }

    @BeforeClass
    public static void securityManagerNotSupportedInHibernateSearch() {
        AssumeTestGroupUtil.assumeSecurityManagerDisabled();
    }

    @Deployment
    public static Archive<?> createTestArchive() throws Exception {

        if (!AssumeTestGroupUtil.isDockerAvailable() || !AssumeTestGroupUtil.isSecurityManagerDisabled()) {
            return AssumeTestGroupUtil.emptyWar(HibernateSearchElasticsearchGsonDependencyTestCase.class.getSimpleName());
        }

        return ShrinkWrap.create(WebArchive.class, HibernateSearchElasticsearchGsonDependencyTestCase.class.getSimpleName() + ".war")
                .addClass(HibernateSearchElasticsearchGsonDependencyTestCase.class)
                .addClasses(SearchBean.class, IndexedEntity.class, AssumeTestGroupUtil.class)
                .addAsResource(manifest(), "META-INF/MANIFEST.MF")
                .addAsResource(persistenceXml(), "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static Asset manifest() {
        String manifest = Descriptors.create(ManifestDescriptor.class)
                .attribute("Dependencies", "com.google.code.gson")
                .exportAsString();
        return new StringAsset(manifest);
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
                .createProperty().name("hibernate.search.automatic_indexing.synchronization.strategy").value("sync").up()
                .createProperty().name("hibernate.search.backend.type").value("elasticsearch").up()
                .createProperty().name("hibernate.search.backend.hosts").value(ElasticsearchServerSetupObserver.getHttpHostAddress()).up()
                .up().up()
                .exportAsString();
        return new StringAsset(persistenceXml);
    }

    @Inject
    private SearchBean searchBean;

    @Test
    public void test() {
        assertEquals(0, searchBean.searchWithNativeQuery("mytoken").size());
        searchBean.create("This is MYToken");
        assertEquals(1, searchBean.searchWithNativeQuery("mytoken").size());
    }

    @ApplicationScoped
    @Transactional
    public static class SearchBean {

        @PersistenceContext
        EntityManager em;

        public void create(String text) {
            IndexedEntity entity = new IndexedEntity();
            entity.text = text;
            em.persist(entity);
        }

        public List<IndexedEntity> searchWithNativeQuery(String keyword) {
            return Search.session(em).search(IndexedEntity.class)
                    .extension(ElasticsearchExtension.get())
                    .where(f -> {
                        JsonObject outer = new JsonObject();
                        JsonObject term = new JsonObject();
                        outer.add("term", term);
                        JsonObject inner = new JsonObject();
                        term.add("text", inner);
                        inner.addProperty("value", keyword);
                        return f.fromJson(outer);
                    })
                    .fetchAllHits();
        }
    }

    @Entity
    @Indexed
    public static class IndexedEntity {

        @Id
        @GeneratedValue
        Long id;

        @FullTextField
        String text;

    }

}
