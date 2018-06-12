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

package org.jboss.additional.testsuite.jdkall.past.eap_7.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * @author Ondrej Chaloupka
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Eap70x/ejb/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap64x/ejb/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap63x/ejb/src/main/java","modules/testcases/jdkAll/Eap62x/ejb/src/main/java","modules/testcases/jdkAll/Eap61x/ejb/src/main/java"})
public class AsyncBeanSynchronizeSingleton implements AsyncBeanSynchronizeSingletonRemote {
    private static volatile CountDownLatch latch = new CountDownLatch(1);
    private static volatile CountDownLatch latch2 = new CountDownLatch(1);

    public void reset() {
        latch = new CountDownLatch(1);
        latch2 = new CountDownLatch(1);
    }
    
    public void latchCountDown() {
        latch.countDown();
    }
    
    public void latch2CountDown() {
        latch2.countDown();
    }

    public void latchAwaitSeconds(int sec) throws InterruptedException {
        latch.await(sec, TimeUnit.SECONDS);
    }
    
    public void latch2AwaitSeconds(int sec) throws InterruptedException {
        latch2.await(sec, TimeUnit.SECONDS);
    }
}
