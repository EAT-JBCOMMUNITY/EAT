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

package org.jboss.additional.testsuite.jdkall.present.clustering.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;

/**
 * {@link EJBDirectory} that uses remote JNDI.
 *
 * NOTE:
 * if you hold a static reference to this class, it causes any JNDI lookups across different tests to use the same discovered node registry (DNR).
 * This can cause server starts and stops in one test to contaminate other tests and produce incorrect results.
 * It is adviseable to use one instance per test by defining a @Before and @After method to create and dispose of the instance on a per test basis.
 *
 * @author Paul Ferraro
 */
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/EapJakarta/clustering/src/main/java"})
public class RemoteEJBDirectory extends NamingEJBDirectory {

    private static Properties createEnvironment() {
        Properties env = new Properties();
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, org.wildfly.naming.client.WildFlyInitialContextFactory.class.getName());
        // TODO UserTransaction lookup currently requires environment to be configured with provider URLs.
        // env.setProperty(Context.PROVIDER_URL, String.join(",", EJBClientContext.getCurrent().getConfiguredConnections().stream().map(EJBClientConnection::getDestination).map(URI::toString).collect(Collectors.toList())));
        return env;
    }

    public RemoteEJBDirectory(String module) throws NamingException {
        this(module, createEnvironment());
    }

    public RemoteEJBDirectory(String module, Properties properties) throws NamingException {
        super(properties, "ejb:", module, "txn:UserTransaction");
    }

    @Override
    protected String createJndiName(String beanName, Class<?> beanInterface, Type type) {
        String jndiName = super.createJndiName(beanName, beanInterface, type);
        switch (type) {
            case STATEFUL: {
                return jndiName + "?stateful=true";
            }
            default: {
                return jndiName;
            }
        }
    }
}
