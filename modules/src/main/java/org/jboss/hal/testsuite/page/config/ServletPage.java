package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.fragment.config.web.servlet.ServletConfigArea;
import org.jboss.hal.testsuite.page.ConfigPage;

@Location("#web")
public class ServletPage extends ConfigPage {

    public ServletConfigArea getConfig() {
        return getConfig(ServletConfigArea.class);
    }

}
