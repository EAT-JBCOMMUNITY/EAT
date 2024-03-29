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
package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.dispatcher.bean;

import java.io.Serializable;
import java.util.Collection;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/clustering/src/main/java"})
public class ClusterTopology implements Serializable {
    private static final long serialVersionUID = 413628123168918069L;

    private final Collection<String> nodes;
    private final String localNode;
    private final Collection<String> remoteNodes;

    public ClusterTopology(Collection<String> nodes, String localNode, Collection<String> remoteNodes) {
        this.nodes = nodes;
        this.localNode = localNode;
        this.remoteNodes = remoteNodes;
    }

    public Collection<String> getNodes() {
        return this.nodes;
    }

    public String getLocalNode() {
        return this.localNode;
    }

    public Collection<String> getRemoteNodes() {
        return this.remoteNodes;
    }
}
