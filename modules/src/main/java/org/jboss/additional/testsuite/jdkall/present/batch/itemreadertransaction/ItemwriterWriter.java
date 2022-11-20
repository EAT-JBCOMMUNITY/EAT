package org.jboss.additional.testsuite.jdkall.present.batch.itemreadertransaction;

import java.io.Serializable;
import java.util.List;

import javax.batch.api.chunk.ItemWriter;
import javax.inject.Named;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Named
@EAT({"modules/testcases/jdkAll/Wildfly/batch/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap7Plus/batch/src/main/java#7.4.5"})
public class ItemwriterWriter implements ItemWriter {

    @Override
    public void open(Serializable checkpoint) throws Exception {
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }

}
