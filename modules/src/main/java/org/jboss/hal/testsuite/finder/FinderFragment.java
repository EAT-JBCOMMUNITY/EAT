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

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Harald Pehl
 */
public abstract class FinderFragment {

    @Drone protected WebDriver browser;
    @Root protected WebElement root;

    public FinderFragment invoke(String action) {
        try {
            root.findElement(primaryButtonSelector(action)).click();

        } catch (NoSuchElementException e) {
            // fallback
            try {
                WebElement dropdownArrow = root.findElement(dropDownArrowSelector());
                dropdownArrow.click();
                Graphene.waitGui().until().element(By.className("popupContent")).is().visible();

                browser.findElement(secondaryButtonSelector(action)).click();

            } catch (NoSuchElementException inner) {
                throw new NoSuchElementException("Unable to find action \"" + action + "\" on " +
                        getClass().getSimpleName() + " using selectors " +
                        primaryButtonSelector(action) + ", " + secondaryButtonSelector(action), inner);
            }
        }
        return this;
    }

    protected abstract By primaryButtonSelector(String name);

    protected abstract By secondaryButtonSelector(String name);

    protected abstract By dropDownArrowSelector();
}
