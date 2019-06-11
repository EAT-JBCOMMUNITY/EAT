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

package org.jboss.additional.testsuite.jdkall.present.messaging.jms.context.auxiliary;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static org.jboss.additional.testsuite.jdkall.present.messaging.jms.context.transactionscoped.auxiliary.AppScopedBean.QUEUE_NAME;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2013 Red Hat inc.
 */
@JMSDestinationDefinition(
        name = QUEUE_NAME,
        interfaceName = "javax.jms.Queue",
        destinationName = "AppScopedBeanQueue"
)
@MessageDriven(
        name = "TransactedMDB",
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
            @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = QUEUE_NAME)
        }
)
@TransactionManagement(value = CONTAINER)
@TransactionAttribute(value = REQUIRED)
@EapAdditionalTestsuite({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/Eap72x/messaging/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/Eap7/messaging/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/Eap71x/messaging/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/messaging/src/main/java","modules/testcases/jdkAll/Eap70x/messaging/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/messaging/src/main/java","modules/testcases/jdkAll/Wildfly/messaging/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/messaging/src/main/java"})
public class TransactedMDB implements MessageListener {

    @Inject
    private JMSContext context;

    @Resource
    private MessageDrivenContext mdbContext;

    public void onMessage(final Message m)
    {
       //System.out.println("TransactedMDB.onMessage");
        try {
            // ignore redelivered message
            if (m.getJMSRedelivered()) {
                return;
            }

            TextMessage message = (TextMessage)m;
            Destination replyTo = m.getJMSReplyTo();

            //System.out.println("got message " + message.getText());
            //System.out.println("replying to " + replyTo);
            context.createProducer()
                   .setJMSCorrelationID(message.getJMSMessageID())
                   .send(replyTo, message.getText());
            //System.out.println("sent reply");
            if (m.getBooleanProperty("rollback")) {
                mdbContext.setRollbackOnly();
                //System.out.println("set mdb as rollback only");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
