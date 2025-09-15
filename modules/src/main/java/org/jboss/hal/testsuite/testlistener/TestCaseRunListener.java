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

package org.jboss.hal.testsuite.testlistener;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

/**
 * <p>{@link RunListener} implementation which encapsulates logic to find out which testcase is currently running
 * or has just finished. This class is intended to be inherited from and subclasses are intended to overwrite mainly
 * {@link #beforeTestCase()} or {@link #afterTestCase()} methods. Inside {@link #afterTestCase()} overwritten method
 * child can call {@link #getCurrentTestCaseCanonicalName()} to find out which test case has just finished.<br />
 * If methods from {@link RunListener} have to be overwritten the super methods has to be called first.</p>
 * Created by pjelinek on May 30, 2016
 */
public abstract class TestCaseRunListener extends RunListener {

    private String currentTestCaseCanonicalName;
    private boolean testCaseJustStarted;

    @Override
    public void testRunStarted(Description description) throws Exception {
        this.testCaseJustStarted = true;
        beforeTestCase();
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        recordTestCaseNameIfRunFirstInTestCase(description);
    }

    @Override
    public void testStarted(Description description) throws Exception {
        recordTestCaseNameIfRunFirstInTestCase(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        afterTestCase();
    }

    protected String getCurrentTestCaseCanonicalName() {
        return currentTestCaseCanonicalName;
    }

    /**
     * Called before any tests of test case have been run. Intended to be overwritten by child.
     */
    protected void beforeTestCase() throws Exception { }

    /**
     * Called after all tests of test case have finished. Intended to be overwritten by child.
     */
    protected void afterTestCase() throws Exception { }

    private void recordTestCaseNameIfRunFirstInTestCase(Description description) {
        if (this.testCaseJustStarted) {
            this.currentTestCaseCanonicalName = description.getTestClass().getCanonicalName();
            this.testCaseJustStarted = false;
        }
    }
}
