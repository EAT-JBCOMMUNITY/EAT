/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.messaging.jms.context.transactionscoped;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.additional.testsuite.jdkall.present.messaging.jms.context.transactionscoped.auxiliary.AppScopedBean;
import org.jboss.additional.testsuite.jdkall.present.messaging.jms.context.transactionscoped.auxiliary.ThreadLauncher;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2013 Red Hat inc.
 */
@RunWith(Arquillian.class)
//@AdditionalTestsuite({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/Eap73x/messaging/src/main/java","modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/Eap71x/messaging/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/messaging/src/main/java","modules/testcases/jdkAll/Eap70x/messaging/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/messaging/src/main/java"})
public class TransactionScopedJMSContextTestCase {

    public static final String QUEUE_NAME = "java:app/AppScopedBeanQueue";

    @Resource(mappedName = "/JmsXA")
    private ConnectionFactory factory;

    @Resource(mappedName = QUEUE_NAME)
    private Queue queue;

    @EJB
    ThreadLauncher launcher;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "TransactionScopedJMSContextTestCase.jar")
                .addPackage(AppScopedBean.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE,
                        "beans.xml");
    }

    @Test
    public void sendAndReceiveWithContext() throws JMSException, InterruptedException {
        int numThreads = 5;
        int numMessages = 10;
        launcher.start(numThreads, numMessages);

        int receivedMessages = 0;
        try(JMSContext context = factory.createContext()) {
            JMSConsumer consumer = context.createConsumer(queue);
            Message m;
            do {
                m = consumer.receive(1000);
                if (m != null) {
                    receivedMessages++;
                }
            }
            while (m != null);
        }

        assertEquals(numThreads * numMessages, receivedMessages);
    }
}
