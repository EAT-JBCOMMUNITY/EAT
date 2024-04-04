package org.jboss.additional.testsuite.jdkall.present.junit;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/junit/src/main/java#7.4.17","modules/testcases/jdkAll/WildflyJakarta/junit/src/main/java#32.0.0"})
public class IndexOutOfBoundsExceptionSimulationTestCase<T> {

    private List<T> attachedObjects = new ArrayList<>(2);

    @Test
    public void simulation(){
        Object[] attachedObjects = new Object[2];

        if (this.attachedObjects != null) {
            // Assume 1:1 relation between existing attachedObjects and state
            for (int i = 0, len = attachedObjects.length; i < len; i++) {
                T attachedObject = this.attachedObjects.get(i);
            }
        }
    }


}
