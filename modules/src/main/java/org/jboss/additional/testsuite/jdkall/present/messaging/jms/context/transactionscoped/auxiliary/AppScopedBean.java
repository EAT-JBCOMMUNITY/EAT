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

package org.jboss.additional.testsuite.jdkall.present.messaging.jms.context.transactionscoped.auxiliary;



import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.transaction.Transactional;
import org.jboss.eap.additional.testsuite.annotations.EAT;


@ApplicationScoped
@Transactional
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/Eap72x/messaging/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/Eap73x/messaging/src/main/java","modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/Eap71x/messaging/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/messaging/src/main/java","modules/testcases/jdkAll/Eap70x/messaging/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/messaging/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/Wildfly/messaging/src/main/java#10.0.0.Final*27.0.0.Beta2","modules/testcases/jdkAll/ServerBeta/messaging/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/messaging/src/main/java"})
public class AppScopedBean {

    public static final String QUEUE_NAME = "java:app/AppScopedBeanQueue";

    @Inject
    private JMSContext context;

    @Resource(lookup = QUEUE_NAME)
    private Queue queue;

    public void sendMessage() {
        JMSProducer producer = context.createProducer();
        producer.send(queue, "a message");
    }

}
