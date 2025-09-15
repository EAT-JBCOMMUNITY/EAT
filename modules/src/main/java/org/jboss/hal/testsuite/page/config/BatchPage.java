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

package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.config.batch.BatchConfigFragment;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by pjelinek on Oct 13, 2015
 */
public class BatchPage extends ConfigurationPage implements Navigatable {

    @Override
    public void navigate() {
        FinderNavigation navi;
        if (ConfigUtils.isDomain()) {
            navi = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                .step(FinderNames.PROFILE, ConfigUtils.getDefaultProfile());
        } else {
            navi = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navi.step(FinderNames.SUBSYSTEM, "Batch").selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }

    public void navigateToSubTab(String subTab) {
        navigate();
        switchSubTab(subTab);
    }

    public BatchConfigFragment getConfigFragment() {
        By contentPanelSelector = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");
        WebElement contentPanel = getContentRoot().findElement(contentPanelSelector);
        return Graphene.createPageFragment(BatchConfigFragment.class, contentPanel);
    }

}
