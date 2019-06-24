/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.clustering;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.TopologyChanged;
import org.infinispan.notifications.cachelistener.event.TopologyChangedEvent;
import org.infinispan.remoting.transport.Address;
import org.jboss.as.clustering.msc.ServiceContainerHelper;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistry;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * EJB that establishes a stable topology.
 * @author Paul Ferraro
 */
@Stateless
@Remote(TopologyChangeListener.class)
@Listener(sync = false)
@EapAdditionalTestsuite({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/Wildfly/clustering/src/main/java#9.0.0*13.0.0","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/clustering/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/clustering/src/main/java","modules/testcases/jdkAll/Eap71x/clustering/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/clustering/src/main/java","modules/testcases/jdkAll/Eap70x/clustering/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/clustering/src/main/java","modules/testcases/jdkAll/Eap72x/clustering/src/main/java#7.2.0.CD12*7.2.0.Beta","modules/testcases/jdkAll/Eap72x-Proposed/clustering/src/main/java#7.2.0.CD12*7.2.0.Beta"})
public class TopologyChangeListenerBean implements TopologyChangeListener {

    @Override
    public void establishTopology(String containerName, String cacheName, long timeout, String... nodes) throws InterruptedException {
        Set<String> expectedMembers = new TreeSet<>(Arrays.asList(nodes));
        ServiceRegistry registry = CurrentServiceContainer.getServiceContainer();
        ServiceName name = ServiceName.JBOSS.append("infinispan", containerName);
        EmbeddedCacheManager cacheContainer = ServiceContainerHelper.findValue(registry, name);
        if (cacheContainer == null) {
            throw new IllegalStateException(String.format("Failed to locate %s", name));
        }
        Cache cache = cacheContainer.getCache(cacheName);
        if (cache == null) {
            throw new IllegalStateException(String.format("Cache %s not found", cacheName));
        }
        cache.addListener(this);
        try
        {
            long start = System.currentTimeMillis();
            long now = start;
            long endTime = start + timeout;
            synchronized (this) {
                Set<String> members = getMembers(cache);
                while (!expectedMembers.equals(members)) {
                    System.out.println(String.format("%s != %s, waiting for a topology change event...", expectedMembers, members));
                    this.wait(endTime - now);
                    now = System.currentTimeMillis();
                    if (now >= endTime) {
                        throw new InterruptedException(String.format("Cache %s/%s failed to establish view %s within %d ms.  Current view is: %s", containerName, cacheName, expectedMembers, timeout, members));
                    }
                    members = getMembers(cache);
                }
                System.out.println(String.format("Cache %s/%s successfully established view %s within %d ms.", containerName, cacheName, expectedMembers, now - start));
            }
        } finally {
            cache.removeListener(this);
        }
    }

    private static Set<String> getMembers(Cache<?, ?> cache) {
        Set<String> members = new TreeSet<>();
        for (Address address: cache.getAdvancedCache().getComponentRegistry().getStateTransferManager().getCacheTopology().getMembers()) {
            members.add(address.toString());
        }
        return members;
    }

    @TopologyChanged
    public void topologyChanged(TopologyChangedEvent<?, ?> event) {
        synchronized (this) {
            this.notify();
        }
    }
}
