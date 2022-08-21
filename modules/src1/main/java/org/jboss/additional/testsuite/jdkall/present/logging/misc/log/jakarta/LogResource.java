/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2017 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.additional.testsuite.jdkall.present.logging.misc.log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/logging/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/logging/src/main/java"})
@Path("/")
public class LogResource {
    private static final Logger LOGGER = Logger.getLogger(LogResource.class.getPackage().getName());

    @GET
    @Path("log")
    @Produces(MediaType.APPLICATION_JSON)
    public Response log() {
        // Log from this resource
        LOGGER.infof("Log message from the LogResource: %s", LoggingUtil.getClassLoader());

        // Log from the library with other log facades
        LoggingUtil.infoJBossLogging("org.jboss.reproducer.log.jboss.logging", "Test message from JBoss Logging");
        LoggingUtil.infoJul("org.jboss.reproducer.log.jul", "Test message from JUL");
        LoggingUtil.infoCommonsLogging("org.jboss.reproducer.log.commons.logging", "Test message from commons-logging");
        LoggingUtil.infoSlf4j("org.jboss.reproducer.log.slf4j", "Test message from slf4j");

        return read();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read() {
        // Check the log directory
        final String logDir = System.getProperty("jboss.server.log.dir");
        if (logDir == null) {
            throw new RuntimeException("Could not resolve jboss.server.log.dir");
        }
        final java.nio.file.Path logFile = Paths.get(logDir, "profile-one.log");
        if (Files.notExists(logFile)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            return Response.ok(Files.readAllLines(logFile, StandardCharsets.UTF_8)).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
