/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
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

package org.jboss.hal.testsuite.cli;

/**
 * @author rhatlapa (rhatlapa@redhat.com)
 * @deprecated use creaper Addresses
 */
public class CliConstants {
    public static final String UNDEFINED = "undefined";

    public static final String WEB_SUBSYSTEM_JSP_CONFIGURATION_ADDRESS = "/subsystem=web/configuration=jsp-configuration";
    public static final String DEFAULT_HOST_VIRTUAL_SERVER_ADDRESS = "/subsystem=web/virtual-server=default-host";
    public static final String VIRTUAL_SERVER_SUBSYSTEM_ADDRESS = "/subsystem=web/virtual-server";
    public static final String WEB_CONTAINER_CONFIGURATION_ADDRESS = "/subsystem=web/configuration=container";
    public static final String WEB_CONTAINER_STATIC_RESOURCES_ADDRESS = "/subsystem=web/configuration=static-resources";
    public static final String WEB_SUBSYSTEM_ADDRESS = "/subsystem=web";
    public static final String DATASOURCES_SUBSYSTEM_ADDRESS = "/subsystem=datasources";
    public static final String DATASOURCES_ADDRESS = "/subsystem=datasources/data-source";
    public static final String XA_DATASOURCES_ADDRESS = "/subsystem=datasources/xa-data-source";
    public static final String DEPLOYMENT_ADDRESS = "/deployment";
    public static final String SERVER_GROUP_ADDRESS = "/server-group";
    public static final String INTERFACE_ADDRESS = "/interface";
    public static final String SOCKET_BINDING_GROUP_ADDRESS = "/socket-binding-group";
    public static final String DEFAULT_SOCKET_BINDING_GROUP_ADDRESS = "/socket-binding-group=standard-sockets";
    public static final String DEFAULT_SOCKET_BINDING_INBOUND_ADDRESS = DEFAULT_SOCKET_BINDING_GROUP_ADDRESS + "/socket-binding";
    public static final String DEFAULT_SOCKET_BINDING_OUTBOUND_LOCAL_ADDRESS = DEFAULT_SOCKET_BINDING_GROUP_ADDRESS + "/local-destination-outbound-socket-binding";
    public static final String DEFAULT_SOCKET_BINDING_OUTBOUND_REMOTE_ADDRESS = DEFAULT_SOCKET_BINDING_GROUP_ADDRESS + "/remote-destination-outbound-socket-binding";
    public static final String SYSTEM_PROPERTY_ADDRESS = "/system-property";
    public static final String MAIL_SESSION_SUBSYSTEM_ADDRESS = "/subsystem=mail/mail-session";
    public static final String RESOURCE_ADAPTERS_SUBSYSTEM_ADDRESS = "/subsystem=resource-adapters";
    public static final String RESOURCE_ADAPTER_ADDRESS = "/subsystem=resource-adapters/resource-adapter";
    public static final String WEB_SERVICES_SUBSYSTEM_ADDRESS = "/subsystem=webservices";
    public static final String MOD_CLUSTER_CONFIG_ADDRESS = "/subsystem=modcluster/mod-cluster-config=configuration";
    public static final String DOMAIN_HTTP_INTERFACE_ADDRESS = "/host=master/core-service=management/management-interface=http-interface";
    public static final String STANDALONE_HTTP_INTERFACE_ADDRESS = "/core-service=management/management-interface=http-interface";
    public static final String CACHE_CONTAINER_ADDRESS = "/subsystem=infinispan/cache-container";
    public static final String DOMAIN_CACHE_CONTAINER_ADDRESS = "/host=master/server=server-one/subsystem=infinispan/cache-container";
    public static final String DEPLOYMENT_SCANNER_ADDRESS = "/subsystem=deployment-scanner/scanner";
    public static final String EE_SUBSYSTEM_ADDRESS = "/subsystem=ee";
    public static final String DOMAIN_SERVER_ONE_PREFIX = "/host=master/server=server-one";
    public static final String TRANSACTIONS_SUBSYSTEM_ADDRESS = "/subsystem=transactions";
    public static final String IIOP_SUBSYSTEM_ADDRESS = "/subsystem=iiop-openjdk";
    public static final String JPA_SUBSYSTEM_ADDRESS = "/subsystem=jpa";
    public static final String EJB3_THREAD_POOL_ADDRESS = "/subsystem=ejb3/thread-pool";
    public static final String EJB3_TIMER_SERVICE_ADDRESS = "/subsystem=ejb3/service=timer-service";
    public static final String EJB3_ASYNC_SERVICE_ADDRESS = "/subsystem=ejb3/service=async";
    public static final String EJB3_REMOTE_SERVICE_ADDRESS = "/subsystem=ejb3/service=remote";
    public static final String EJB3_BEAN_POOL_ADDRESS = "/subsystem=ejb3/strict-max-bean-instance-pool";
}
