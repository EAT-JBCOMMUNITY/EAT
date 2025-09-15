package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.page.BasePage;

/**
 * @author jcechace
 *
 * This class represents a meta page entry point to the Runtime part of the consle in domain.
 * As such it is meant for navigation purposes only and thus can't be instantiated. Also note
 * that the actual landing page is determined by console and may change in the future.
 *
 */
@Location("#hosts")
public class DomainRuntimeEntryPoint extends BasePage {
}
