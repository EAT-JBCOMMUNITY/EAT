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

package org.jboss.hal.testsuite.fragment.config.batch;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;

/**
 * Created by pjelinek on Oct 15, 2015
 */
public class BatchConfigFragment extends ConfigFragment {

    public WizardWindow openAddWizard() {
        return getResourceManager().addResource();
    }

    /**
     * @see ResourceTableFragment#selectRowByText(int, String)
     */
    public ResourceTableRowFragment selectTableRow(String name) {
        return getResourceManager().getResourceTable().selectRowByText(0, name);
    }

    /**
     * @see ResourceTableFragment#getRowByText(int, String)
     */
    public ResourceTableRowFragment getTableRow(String name) {
        return getResourceManager().getResourceTable().getRowByText(0, name);
    }
}
