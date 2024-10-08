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

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * This is here as just a hack to be able to set the security domain of a deployment.
 * See https://issues.jboss.org/browse/JBEAP-8702 discussion for more context.
 * @author Jan Martiska
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Final","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/elytron/src/main/java"})
@Stateless
@SecurityDomain("BatchDomain2")
@LocalBean
public class SecurityDomainSettingEJB {
}
