/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.elytron.batch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import jakarta.batch.api.Batchlet;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.jboss.as.test.shared.TimeoutUtil;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Jan Martiska
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/elytron/src/main/java"})
@Named
public class LongRunningBatchlet implements Batchlet {

    private final CompletableFuture<Void> SHOULD_STOP = new CompletableFuture<>();

    @Inject
    JobContext ctx;

    @Override
    public String process() throws Exception {
        SHOULD_STOP.get(TimeoutUtil.adjust(10), TimeUnit.SECONDS);
        return null;
    }

    @Override
    public void stop() throws Exception {
        SHOULD_STOP.complete(null);
    }
}
