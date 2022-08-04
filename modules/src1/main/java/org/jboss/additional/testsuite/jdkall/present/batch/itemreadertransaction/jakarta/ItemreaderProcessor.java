package org.jboss.additional.testsuite.jdkall.present.batch.itemreadertransaction;

import jakarta.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Named
@EAT({"modules/testcases/jdkAll/WildflyJakarta/batch/src/main/java#27.0.0.Alpha4"})
public class ItemreaderProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        return item;
    }

}
