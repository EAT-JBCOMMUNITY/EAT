/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
package org.jboss.hal.testsuite.dmr;

import org.jboss.dmr.ModelNode;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * Represents the response of {@link Dispatcher#execute(Operation)}.
 *
 * @author Harald Pehl
 * @deprecated use creaper
 */
public class DmrResponse {

    private final ModelNode response;

    public DmrResponse(ModelNode response) {
        this.response = response;
    }

    public boolean isSuccessful() {
        return response.get(OUTCOME).asString().equals(SUCCESS);
    }

    /**
     * @return the payload which is extracted from the nested {@link org.jboss.as.controller.descriptions.ModelDescriptionConstants#RESULT}
     * node.
     */
    public ModelNode payload() {
        return response.get(RESULT);
    }

    public ModelNode getFailureDescription() {
        return response.get(FAILURE_DESCRIPTION);
    }
}
