/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.secondlevelcache;

import java.util.Properties;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.transaction.jta.platform.internal.JBossAppServerJtaPlatform;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Madhumita Sadhukhan
 */
@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/jpa/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/jpa/src/main/java","modules/testcases/jdkAll/Wildfly/jpa/src/main/java#13.0.0.Final*27.0.0.Alpha3","modules/testcases/jdkAll/ServerBeta/jpa/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/jpa/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/jpa/src/main/java","modules/testcases/jdkAll/Eap72x/jpa/src/main/java#7.2.0.CD13","modules/testcases/jdkAll/Eap73x/jpa/src/main/java","modules/testcases/jdkAll/Eap7Plus/jpa/src/main/java"})
public class SFSB {

    private static SessionFactory sessionFactory;

    public void cleanup() {
        sessionFactory.close();
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void setupConfig() {
        // static {
        try {

            // prepare the configuration
            Configuration configuration = new Configuration().setProperty("hibernate.id.new_generator_mappings", "true");
            configuration.getProperties().put(AvailableSettings.JTA_PLATFORM, JBossAppServerJtaPlatform.class);
            configuration.getProperties().put(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jta");
            configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            configuration.setProperty("hibernate.connection.datasource", "java:jboss/datasources/ExampleDS");
            // fetch the properties
            Properties properties = new Properties();
            configuration = configuration.configure("hibernate.cfg.xml");
            properties.putAll(configuration.getProperties());
            // Environment.verifyProperties(properties);
            ConfigurationHelper.resolvePlaceHolders(properties);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable ex) { // Make sure you log the exception, as it might be swallowed
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    // create student
    public Student createStudent(String firstName, String lastName, String address, int id) {
        // setupConfig();
        Student student = new Student();
        student.setStudentId(id);
        student.setAddress(address);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        try {
            Session session = sessionFactory.openSession();
            // Hibernate ORM 5.2+ doesn't allow beginTransaction in an active JTA transaction, as openSession
            // will automatically join the JTA transaction.
            // See https://github.com/hibernate/hibernate-orm/wiki/Migration-Guide---5.2
            //Transaction ormTransaction = session.beginTransaction(); // join the current JTA transaction
            //TransactionStatus status = ormTransaction.getStatus();
            //if(status.isNotOneOf(TransactionStatus.ACTIVE)) {
            //    throw new RuntimeException("Hibernate Transaction is not active after joining Hibernate to JTA transaction: " + status.name());
            //}
            session.save(student);
            // trans.commit();
            session.close();
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Failure while persisting student entity", e);

        }
        return student;
    }

    // get student
    public Student getStudent(int id) {
        Student student;

        try {
            Session session = sessionFactory.openSession();
            // Hibernate ORM 5.2+ doesn't allow beginTransaction in an active JTA transaction, as openSession
            // will automatically join the JTA transaction.
            // See https://github.com/hibernate/hibernate-orm/wiki/Migration-Guide---5.2
            //Transaction ormTransaction = session.beginTransaction(); // join the current JTA transaction
            //TransactionStatus status = ormTransaction.getStatus();
            //if(status.isNotOneOf(TransactionStatus.ACTIVE)) {
            //    throw new RuntimeException("Hibernate Transaction is not active after joining Hibernate to JTA transaction: " + status.name());
            //}
            student = session.load(Student.class, id);
            session.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Failure while loading student entity", e);

        }
        return student;
    }


    public void clearCache() {
        sessionFactory.getCache().evictAllRegions();

    }
}
