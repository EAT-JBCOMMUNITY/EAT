package org.jboss.hal.testsuite.fragment.config.datasource;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;

/**
 * @author Jan Kasik
 */
public class PoolConfig extends ConfigFragment {

    private static final String MIN_POOL_SIZE_ID = "minPoolSize";
    private static final String MAX_POOL_SIZE_ID = "maxPoolSize";

    public boolean setMinPoolSize(String value) {
        return setTextValueAndSave(MIN_POOL_SIZE_ID, value);
    }

    public boolean setMaxPoolSize(String value) {
        return setTextValueAndSave(MAX_POOL_SIZE_ID, value);
    }

    public boolean setTextValueAndSave(String identifier, String value) {
        Editor editor = edit();
        editor.text(identifier, value);
        Boolean result = save();
        return result;
    }

    public boolean setMinMaxPoolSizeAndSave(String min, String max) {
        Editor editor = edit();
        editor.text(MIN_POOL_SIZE_ID, min);
        editor.text(MAX_POOL_SIZE_ID, max);
        Boolean result = save();
        return result;
    }

    public void setCheckboxValueAndSave(String identifier, Boolean set) {
        Editor editor = edit();
        editor.checkbox(identifier, set);
        save();
    }

}
