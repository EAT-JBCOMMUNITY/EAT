/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.elytron.rolemappers;

import java.util.List;
import org.wildfly.test.security.common.elytron.ConfigurableElement;
import org.wildfly.test.security.common.elytron.SimpleSecurityDomain;
import org.wildfly.test.security.common.elytron.UndertowDomainMapper;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 *
 * @author olukas
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/Wildfly/elytron/src/main/java","modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/ServerBeta/elytron/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/Eap72x/elytron/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/elytron/src/main/java","modules/testcases/jdkAll/Eap73x/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/elytron/src/main/java","modules/testcases/jdkAll/Eap71x/elytron/src/main/java"})
class RoleMapperSetupUtils {

    static final String PROPERTIES_REALM_NAME = "RoleMapperPropertiesRealm";

    private RoleMapperSetupUtils() {
    }

    static void addSecurityDomainWithRoleMapper(List<ConfigurableElement> elements, String roleMapperName) {
        elements.add(SimpleSecurityDomain.builder().withName(roleMapperName)
                .withRoleMapper(roleMapperName)
                .withDefaultRealm(PROPERTIES_REALM_NAME)
                .withPermissionMapper("default-permission-mapper")
                .withRealms(SimpleSecurityDomain.SecurityDomainRealm.builder()
                        .withRealm(PROPERTIES_REALM_NAME)
                        .withRoleDecoder("groups-to-roles")
                        .build())
                .build());
        elements.add(UndertowDomainMapper.builder()
                .withName(roleMapperName)
                .withApplicationDomains(roleMapperName)
                .build());
    }

}
