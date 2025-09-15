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
package org.jboss.hal.testsuite.finder;

import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

/**
 * A fragment representing a column in a finder.
 *
 * @author Harald Pehl
 */
public class Column extends FinderFragment {

    @Override
    protected By primaryButtonSelector(final String name) {
        return ByJQuery.selector(".nav-headerMenu div.primary:contains('" + name + "')");
    }

    @Override
    protected By secondaryButtonSelector(final String name) {
        return ByJQuery.selector(".popupContent td.gwt-MenuItem:contains('" + name + "')");
    }

    @Override
    protected By dropDownArrowSelector() {
        return By.cssSelector(".btn.dropdown-toggle.primary");
    }

    /**
     * Wait for up to {@code timeout} to row containing text {@code label} to appear.<br />
     * Intended to be used in e.g. large domain tests.
     * @param label
     * @param timeout
     * @param navi
     * @return
     */
    public boolean rowIsPresent(String label, Long timeout, FinderNavigation navi) {
        By rowSelector = navi.rowSelector(label);
        try {
            Graphene.waitModel().withTimeout(timeout, TimeUnit.SECONDS).until().element(root, rowSelector).is().present();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
