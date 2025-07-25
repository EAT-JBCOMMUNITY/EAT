/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;


/**
 * Stateless session bean invoked asynchronously.
 */
@Stateless
@Asynchronous
@LocalBean
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java#10.0.0.Final*27.0.0.Beta2","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/ejb/src/main/java"})
public class AsyncBean implements AsyncBeanCancelRemoteInterface {
    public static volatile boolean voidMethodCalled = false;
    public static volatile boolean futureMethodCalled = false;

    @Inject
    private RequestScopedBean requestScopedBean;
    
    @Resource
    SessionContext ctx;
    
    @EJB
    AsyncBeanSynchronizeSingletonRemote synchronizeBean;

    public void asyncMethod(CountDownLatch latch, CountDownLatch latch2) throws InterruptedException {
        latch.await(5, TimeUnit.SECONDS);
        voidMethodCalled = true;
        latch2.countDown();
    }

    public Future<Boolean> futureMethod(CountDownLatch latch) throws InterruptedException {
        latch.await(5, TimeUnit.SECONDS);
        futureMethodCalled = true;
        return new AsyncResult<Boolean>(true);
    }

    public Future<Integer> testRequestScopeActive(CountDownLatch latch) throws InterruptedException {
        latch.await(5, TimeUnit.SECONDS);
        requestScopedBean.setState(20);
        return new AsyncResult<Integer>(requestScopedBean.getState());
    }

    public Future<String> asyncCancelMethod(CountDownLatch latch, CountDownLatch latch2) throws InterruptedException {
        String result;
        result = ctx.wasCancelCalled() ? "true" : "false";
        
        latch.countDown();
        latch2.await(5, TimeUnit.SECONDS);
        
        result += ";";
        result += ctx.wasCancelCalled() ? "true" : "false";
        return new AsyncResult<String>(result);
    }
    
    public Future<String> asyncRemoteCancelMethod() throws InterruptedException {
        String result = "";
        result = ctx.wasCancelCalled() ? "true" : "false";
        
        synchronizeBean.latchCountDown();
        synchronizeBean.latch2AwaitSeconds(5);
        
        result += ";";

        result += ctx.wasCancelCalled() ? "true" : "false";
        return new AsyncResult<String>(result);
    }
    
    public Future<String> asyncMethodWithException(boolean isException) {
        if(isException) {
            throw new IllegalArgumentException(); //some exception is thrown
        }
        return new AsyncResult<String>("Hi");
    }
}
