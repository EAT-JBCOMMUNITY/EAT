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

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;

/**
 * A fragment representing a row in a finder.
 *
 * @author Harald Pehl
 */
public class Row extends FinderFragment {

    @Override
    protected By primaryButtonSelector(String name) {
        return ByJQuery.selector("button#" + name + ":visible," +
                "button:contains('" + name + "'):visible," +
                "div.btn:contains('" + name + "'):visible");
    }

    @Override
    protected By secondaryButtonSelector(final String name) {
        return ByJQuery.selector(".popupContent td.gwt-MenuItem:contains('" + name + "')");
    }

    @Override
    protected By dropDownArrowSelector() {
        return By.cssSelector(".btn.dropdown-toggle");
    }

    public String getText() {
        return root.getText();
    }
}
