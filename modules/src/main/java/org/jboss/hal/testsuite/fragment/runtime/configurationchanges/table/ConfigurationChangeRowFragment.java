package org.jboss.hal.testsuite.fragment.runtime.configurationchanges.table;

import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Class representing row in configuration changes table
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 9/12/16.
 */
public class ConfigurationChangeRowFragment extends ResourceTableRowFragment {

    private static final int DATETIME_CELL_INDEX = 0;
    private static final int ACCESS_MECHANISM_CELL_INDEX = 1;
    private static final int REMOTE_ADDRESS_CELL_INDEX = 2;
    private static final int RESOURCE_ADDRESS_CELL_INDEX = 3;
    private static final int OPERATION_CELL_INDEX = 4;
    private static final int RESULT_CELL_INDEX = 5;

    public Date getDatetime() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        String datetimeString = getCellValue(DATETIME_CELL_INDEX);
        return format.parse(datetimeString);
    }

    public String getAccessMechanism() {
        return getCellValue(ACCESS_MECHANISM_CELL_INDEX);
    }

    public String getRemoteAddress() {
        return getCellValue(REMOTE_ADDRESS_CELL_INDEX);
    }

    public String getResourceAddress() {
        return getCellValue(RESOURCE_ADDRESS_CELL_INDEX);
    }

    public String getOperation() {
        return getCellValue(OPERATION_CELL_INDEX);
    }
    public String getResult() {
        return getCellValue(RESULT_CELL_INDEX);
    }
}
