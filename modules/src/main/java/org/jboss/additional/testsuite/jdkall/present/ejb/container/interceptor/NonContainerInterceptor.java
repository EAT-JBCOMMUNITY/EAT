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

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Map;

import org.jboss.logging.Logger;
import org.jboss.eap.additional.testsuite.annotations.EAT;
/**
 * Simple interceptor, which adds its classname in front of the result of {@link InvocationContext#proceed()}. Result of the
 * proceed() stays untouched in case the {@link InvocationContext#getContextData()} contains classname of this interceptor under
 * the {@link FlowTrackingBean#CONTEXT_DATA_KEY} key.
 *
 * @author Jaikiran Pai
 */
@EAT({"modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.8"})
public class NonContainerInterceptor {

    private static final Logger logger = Logger.getLogger(NonContainerInterceptor.class);

    @AroundInvoke
    public Object someMethod(InvocationContext invocationContext) throws Exception {
        logger.trace("Invoked non-container interceptor!!!");
        Map<String, Object> contextData = invocationContext.getContextData();
        System.out.println("contextData = " + contextData);

        Object returnObject = invocationContext.proceed();

        contextData.remove("AnyKey");
        System.out.println("Removed contextData = " + contextData);

        return returnObject;

    }
}
