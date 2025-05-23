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

package org.jboss.additional.testsuite.jdkall.present.messaging.jms.deployment;

import static org.jboss.additional.testsuite.jdkall.present.messaging.jms.deployment.DependentMessagingDeploymentTestCase.QUEUE_LOOKUP;
import static org.jboss.additional.testsuite.jdkall.present.messaging.jms.deployment.DependentMessagingDeploymentTestCase.TOPIC_LOOKUP;

import java.io.IOException;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.Destination;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.Topic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;


/**
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2014 Red Hat inc.
 */
@WebServlet("/DependentMessagingDeploymentTestCase")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/EapJakarta/messaging/src/main/java"})
public class MessagingServlet extends HttpServlet {

    @Resource(lookup = QUEUE_LOOKUP)
    private Queue queue;

    @Resource(lookup = TOPIC_LOOKUP)
    private Topic topic;

    @Inject
    private JMSContext context;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean useTopic = req.getParameterMap().keySet().contains("topic");
        final Destination destination = useTopic ? topic : queue;
        final String text = req.getParameter("text");

        String reply = sendAndReceiveMessage(destination, text);

        resp.getWriter().write(reply);
    }

    private String sendAndReceiveMessage(Destination destination, String text) {
            Destination replyTo = context.createTemporaryQueue();

            JMSConsumer consumer = context.createConsumer(replyTo);

            context.createProducer()
                    .setJMSReplyTo(replyTo)
                    .send(destination, text);

            return consumer.receiveBody(String.class, 5000);
    }
}
