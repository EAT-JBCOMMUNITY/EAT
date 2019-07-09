/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.ejb.stateful.passivation;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java#16.0.0.Beta1","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x/ejb/src/main/java#7.2.1", "modules/testcases/jdkAll/Eap72x-Proposed/ejb/main/java#7.2.1", "modules/testcases/jdkAll/Eap7/ejb/src/main/java"})
public interface Bean extends AutoCloseable {
    /**
     * Returns whether or not this instance has been passivated
     */
    boolean wasPassivated();

    /**
     * Returns whether or not this instance has been activated
     */
    boolean wasActivated();

    default void doNothing() {
    }

    @Override
    void close();
}
