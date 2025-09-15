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

import org.jboss.hal.testsuite.util.ConfigUtils;

import java.util.LinkedList;

/**
 * The default context implementation for the testsuite.
 * <p>
 * This implementation will handle the following patterns:
 * <ul>
 * <li>{@code {default.profile}} &rarr; {@code /profile=full} if running in domain mode, otherwise this pattern will
 * simply be skipped.</li>
 * </ul>
 *
 * @author Harald Pehl
 * @deprecated use creaper
 */
public class DefaultContext implements StatementContext {

    public static final String DEFAULT_PROFILE_KEY = "default.profile";
    public static final String DEFAULT_PROFILE_VALUE = "full";

    @Override
    public String get(String key) {
        return resolve(key);
    }

    @Override
    public String resolve(final String key) {
        return null;
    }

    @Override
    public String[] getTuple(String key) {
        return resolveTuple(key);
    }

    @Override
    public String[] resolveTuple(final String key) {
        if (DEFAULT_PROFILE_KEY.equals(key) && ConfigUtils.isDomain()) {
            return new String[]{"profile", DEFAULT_PROFILE_VALUE};
        }
        return null;
    }

    @Override
    public LinkedList<String> collect(final String key) {
        LinkedList<String> items = new LinkedList<>();
        String value = resolve(key);
        if (value != null) {
            items.add(value);
        }
        return items;
    }

    @Override
    public LinkedList<String[]> collectTuples(final String key) {
        LinkedList<String[]> items = new LinkedList<>();
        String[] tuple = resolveTuple(key);
        if (tuple != null) {
            items.add(tuple);
        }
        return items;
    }
}
