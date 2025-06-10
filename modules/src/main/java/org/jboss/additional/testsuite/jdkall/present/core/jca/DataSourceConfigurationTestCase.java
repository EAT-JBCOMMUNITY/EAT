package org.jboss.eap.jca.ds.h2;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.util.logging.Level;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Assert;

@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyJakarta/core/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/core/src/main/java","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.4","modules/testcases/jdkAll/EapJakarta/core/src/main/java"})
public class DataSourceConfigurationTestCase {

    @Deployment
    public static Archive<?> deploy() throws Exception {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "testdo.war");
        war.addClasses(DataSourceConfigurationTestCase.class);
        war.addAsManifestResource(new StringAsset("Dependencies: com.h2database.h2\n"), "MANIFEST.MF");
        return war;
    }

    @Resource(mappedName = "java:module/UserTransaction")
    private UserTransaction trans;
    @Resource(mappedName = "java:jboss/datasources/ExampleDS")
    DataSource ds;

    @Test
    public void testDataSourceAutocommit() throws Exception {
        Connection rawConnection = null;
        try {
            trans.begin();
            try (Connection connection = ds.getConnection()) {
                rawConnection = connection.unwrap(org.h2.jdbc.JdbcConnection.class);
            }
            trans.commit();
            if (rawConnection.getAutoCommit()) {
                // This is fine ...
            } else {
                Assert.fail("Autocommit should be true after transaction is committed ...");
            }
        } catch (Throwable t) {
            try {
                trans.rollback();
            } catch (Throwable rf) {
                System.out.println(Level.SEVERE + " " + rf.getMessage());
            } finally {
                throw t;
            }
        }

    }

}
