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

package org.jboss.additional.testsuite.jdkall.present.ejb.remote.client;

import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.SessionBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.jboss.eap.additional.testsuite.annotations.EAT;


/**
 * @author Paul Ferraro
 *
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java"})
public abstract class AbstractEJBDirectory implements EJBDirectory {
    private final Context context;
    private final String txContextName;

    protected enum Type {
        STATEFUL, STATELESS, SINGLETON, HOME
    }

    protected AbstractEJBDirectory(String txContextName, Properties env) throws NamingException {
        this(txContextName, new InitialContext(env));
    }

    protected AbstractEJBDirectory(String txContextName, InitialContext context) {
        this.context = context;
        this.txContextName = txContextName;
    }

    @Override
    public void close() throws NamingException {
        this.context.close();
    }

    @Override
    public <T> T lookupStateful(Class<? extends T> beanClass, Class<T> beanInterface) throws NamingException {
        return this.lookupStateful(beanClass.getSimpleName(), beanInterface);
    }

    @Override
    public <T> T lookupStateful(String beanName, Class<T> beanInterface) throws NamingException {
        return this.lookup(beanName, beanInterface, Type.STATEFUL);
    }

    @Override
    public <T> T lookupStateless(Class<? extends T> beanClass, Class<T> beanInterface) throws NamingException {
        return this.lookupStateless(beanClass.getSimpleName(), beanInterface);
    }

    @Override
    public <T> T lookupStateless(String beanName, Class<T> beanInterface) throws NamingException {
        return this.lookup(beanName, beanInterface, Type.STATELESS);
    }

    @Override
    public <T> T lookupSingleton(Class<? extends T> beanClass, Class<T> beanInterface) throws NamingException {
        return this.lookupSingleton(beanClass.getSimpleName(), beanInterface);
    }

    @Override
    public <T> T lookupSingleton(String beanName, Class<T> beanInterface) throws NamingException {
        return this.lookup(beanName, beanInterface, Type.SINGLETON);
    }

    @Override
    public <T extends EJBHome> T lookupHome(Class<? extends SessionBean> beanClass, Class<T> homeInterface) throws NamingException {
        return this.lookupHome(beanClass.getSimpleName(), homeInterface);
    }

    @Override
    public <T extends EJBHome> T lookupHome(String beanName, Class<T> homeInterface) throws NamingException {
        return this.lookup(beanName, homeInterface, Type.HOME);
    }

    @Override
    public UserTransaction lookupUserTransaction() throws NamingException {
        return this.lookup(this.txContextName, UserTransaction.class);
    }

    protected <T> T lookup(String beanName, Class<T> beanInterface, Type type) throws NamingException {
        return this.lookup(this.createJndiName(beanName, beanInterface, type), beanInterface);
    }

    protected abstract <T> String createJndiName(String beanName, Class<T> beanInterface, Type type);

    protected <T> T lookup(String name, Class<T> targetClass) throws NamingException {
        return targetClass.cast(this.context.lookup(name));
    }
}
