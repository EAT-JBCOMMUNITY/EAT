package org.jboss.hal.testsuite.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.page.BasePage;

/**
 * Created by jcechace on 21/02/14.
 */
public class ConfigUtils {

    private static final Properties config = new Properties();
    static {
        // Load default configuration
        try {
            InputStream defaultConfig = PropUtils.class.getResourceAsStream("/suite.properties");
            try {
                config.load(defaultConfig);
            } finally {
                defaultConfig.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load suite configuration");
        }

        // Override by custom configuration
        String customConfigLocation = System.getProperty("suite.config.location");
        if (customConfigLocation != null) {
            try {
                InputStream customConfig = new FileInputStream(customConfigLocation);
                try {
                    config.load(customConfig);
                } finally {
                    customConfig.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to load custom suite configuration");
            }
        }

        // Override by properties from command line
        config.putAll(System.getProperties());

    }


    public static String get(String key) {
        return config.getProperty(key);
    }

    public static String get(String key, String defval) {
        return config.getProperty(key, defval);
    }

    public static boolean isEAP() {
        return get("suite.server", "eap").toLowerCase().equals("eap");
    }

    public static boolean isDomain() {
        return get("suite.mode", "standalone").toLowerCase().equals("domain");
    }
    public static String getDistHome() {
        return get("jboss.dist");
    }

    public static String getDefaultProfile() {
        return get("suite.domain.default.profile", "full");
    }

    public static String getDefaultHost() {
        return get("suite.domain.default.host", "master");
    }

    public static URL getUrl() {
        try {
            URL url = new URL(System.getProperty("suite.url"));
            return url;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid url", e);
        }
    }

    public static String getPageLocation(Class<? extends BasePage> page) {
        if (!page.isAnnotationPresent(Location.class)) {
            throw new IllegalArgumentException("Location annotation is not present on given page");
        }

        Location location = page.getAnnotation(Location.class);

        return location.value();
    }

    public static String getConfigDirPathName() {
        return "jboss." + (isDomain() ? "domain" : "server") + ".config.dir";
    }

}
