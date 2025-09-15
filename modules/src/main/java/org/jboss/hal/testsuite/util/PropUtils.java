package org.jboss.hal.testsuite.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jcechace on 15/02/14.
 */
public class PropUtils {

    private static final Properties props = new Properties();
    static {
        try {
            InputStream in = PropUtils.class.getResourceAsStream("/label.properties");
            try {
                props.load(in);
                props.putAll(System.getProperties());
                String pass = System.getenv("RHACCESS_PASSWORD");
                if (pass != null) {
                    props.put("rhaccess.password", pass);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load label.properties");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
