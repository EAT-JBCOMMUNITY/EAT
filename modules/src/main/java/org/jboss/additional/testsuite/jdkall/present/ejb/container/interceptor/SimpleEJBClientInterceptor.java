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

import java.util.Map;

import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.jboss.eap.additional.testsuite.annotations.EAT;
/**
 * An EJB client side interceptor. The entries in a {@link Map} provided to the constructor are copied to the
 * {@link EJBClientInvocationContext#getContextData()} in the {@link #handleInvocation(EJBClientInvocationContext)} before
 * {@link EJBClientInvocationContext#sendRequest()} is called
 *
 * @author Jaikiran Pai
 */
@EAT({"modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.8"})
public class SimpleEJBClientInterceptor implements EJBClientInterceptor {

    private final Map<String, Object> data;

    SimpleEJBClientInterceptor(final Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public void handleInvocation(EJBClientInvocationContext context) throws Exception {
        // add all the data to the EJB client invocation context so that it becomes available to the server side
        context.getContextData().put("AnyKey", "AnyValue");
        System.out.println("Added context data!");
        // proceed "down" the invocation chain
        context.sendRequest();
    }

    @Override
    public Object handleInvocationResult(EJBClientInvocationContext context) throws Exception {
        // we don't have anything special to do with the result so just return back the result
        // "up" the invocation chain
        System.out.println("Rcontext " + context.getContextData());
        return context.getResult();
    }
}
