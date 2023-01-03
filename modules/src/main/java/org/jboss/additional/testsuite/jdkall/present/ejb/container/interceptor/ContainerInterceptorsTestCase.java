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
package org.jboss.additional.testsuite.jdkall.present.ejb.container.interceptor;

import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import static org.hamcrest.CoreMatchers.containsString;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;
/**
 * Tests that the <code>container-interceptors</code> configured in jboss-ejb3.xml and processed and applied correctly to the
 * relevant EJBs.
 *
 * @author Jaikiran Pai
 */
/**
 * HOW TO TEST : TEST WITH WIRESHARK. "AnyKey=AnyValue" should not exist in the DATA.
 */
// Should not be distributed. It is a manual testcase that needs to start the server manually
// 1. Start the configured server and wireshark, 2. Run the manual test, 3. Check data at HTTP2 HEADERS[3]: 200 OK, DATA[3] 
//@AT({"modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.10"})
@RunWith(Arquillian.class)
public class ContainerInterceptorsTestCase {

    private static final String EJB_JAR_NAME = "test";

    @Deployment(name=EJB_JAR_NAME)
    public static JavaArchive createDeployment() {
        final JavaArchive jar = ShrinkWrap.create(JavaArchive.class, EJB_JAR_NAME + ".jar");
        jar.addPackage(ContainerInterceptorsTestCase.class.getPackage());
        return jar;
    }

    @Test
    // force real remote invocation so that the RemotingConnectionEJBReceiver is used instead of a LocalEJBReceiver
    @RunAsClient
    public void testDataPassingForContainerInterceptorsOnRemoteView() throws Exception {
        // create some data that the client side interceptor will pass along during the EJB invocation
        final Map<String, Object> interceptorData = new HashMap<String, Object>();
        interceptorData.put(FlowTrackingBean.CONTEXT_DATA_KEY, NonContainerInterceptor.class.getName());
        final SimpleEJBClientInterceptor clientInterceptor = new SimpleEJBClientInterceptor(interceptorData);
        // get hold of the EJBClientContext and register the client side interceptor
        EJBClientContext ejbClientContext = EJBClientContext.getCurrent().withAddedInterceptors(clientInterceptor);

        final Hashtable<String, Object> jndiProps = new Hashtable<String, Object>();
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context jndiCtx = new InitialContext(jndiProps);
        ejbClientContext.runCallable(() -> {
            final FlowTracker bean = (FlowTracker) jndiCtx.lookup("ejb:/" + EJB_JAR_NAME + "/"
                    + FlowTrackingBean.class.getSimpleName() + "!" + FlowTracker.class.getName());
            final String message = "foo";
            final String firstResult = bean.echo(message);
            System.out.println("========= " + firstResult);

            // This assertion is always true. Should be checked with wireshark.
            Assert.assertTrue("AnyKey=AnyValue should not be contained in the result",
                    !firstResult.contains("AnyKey=AnyValue")); 

           
            return null;
        });
    }

     @Test
    // force real remote invocation so that the RemotingConnectionEJBReceiver is used instead of a LocalEJBReceiver
    @RunAsClient
    public void testDataPassingForContainerInterceptorsOnRemoteViewUsingHttpClient() throws Exception {
        // create some data that the client side interceptor will pass along during the EJB invocation
        final Map<String, Object> interceptorData = new HashMap<String, Object>();
        interceptorData.put(FlowTrackingBean.CONTEXT_DATA_KEY, NonContainerInterceptor.class.getName());
        final SimpleEJBClientInterceptor clientInterceptor = new SimpleEJBClientInterceptor(interceptorData);
        // get hold of the EJBClientContext and register the client side interceptor
        EJBClientContext ejbClientContext = EJBClientContext.getCurrent().withAddedInterceptors(clientInterceptor);

        final Hashtable<String, Object> jndiProps = new Hashtable<String, Object>();
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProps.put(Context.PROVIDER_URL, "http://localhost:8080/wildfly-services");
        jndiProps.put(Context.SECURITY_PRINCIPAL, "ANONYMOUS");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "ANONYMOUS");
        final Context jndiCtx = new InitialContext(jndiProps);
        ejbClientContext.runCallable(() -> {
            final FlowTracker bean = (FlowTracker) jndiCtx.lookup("ejb:/" + EJB_JAR_NAME + "/"
                    + FlowTrackingBean.class.getSimpleName() + "!" + FlowTracker.class.getName());
            final String message = "foo";
            final String firstResult = bean.echo(message);
            System.out.println("========= " + firstResult);

            // This assertion is always true. Should be checked with wireshark.
            Assert.assertTrue("AnyKey=AnyValue should not be contained in the result",
                    !firstResult.contains("AnyKey=AnyValue"));

           
            return null;
        });
    }

}
