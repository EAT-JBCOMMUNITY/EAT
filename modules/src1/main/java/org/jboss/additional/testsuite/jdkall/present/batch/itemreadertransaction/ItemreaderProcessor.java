package org.jboss.additional.testsuite.jdkall.present.batch.itemreadertransaction;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Named
@EAT({"modules/testcases/jdkAll/Wildfly/batch/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/batch/src/main/java","modules/testcases/jdkAll/Eap7Plus/batch/src/main/java#7.4.3"})
public class ItemreaderProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        return item;
    }

}
