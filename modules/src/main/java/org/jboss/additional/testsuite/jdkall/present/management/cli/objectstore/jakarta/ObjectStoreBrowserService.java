/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.management.cli.objectstore;

import com.arjuna.ats.arjuna.tools.osb.api.mbeans.RecoveryStoreBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Instantiates RecoveryStoreBean on JMX.
 *
 * @author <a href="mailto:istudens@redhat.com">Ivo Studensky</a>
 */
@Singleton
@Startup
@EAT({"modules/testcases/jdkAll/WildflyJakarta/management/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/management/src/main/java"})
public class ObjectStoreBrowserService {

    private RecoveryStoreBean rsb;

    @PostConstruct
    public void start() {
        rsb = new RecoveryStoreBean();
        rsb.start();
    }

    @PreDestroy
    public void stop() {
        if (rsb != null)
            rsb.stop();
    }

}
