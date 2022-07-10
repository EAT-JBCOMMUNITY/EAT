package org.jboss.additional.testsuite.jdkall.present.batch.itemreadertransaction;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionSynchronizationRegistry;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Named
@EAT({"modules/testcases/jdkAll/Wildfly/batch/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/batch/src/main/java","modules/testcases/jdkAll/Eap7Plus/batch/src/main/java#7.4.5"})
public class ItemreaderReader implements ItemReader {

    @Inject
    @BatchProperty
    private int limit;

    @Inject
    private JobContext context;

    @Inject
    private JobContext jobContext;

    private int count = 0;

    @Resource
    private TransactionSynchronizationRegistry reg;

    @Inject
    private StepScopedBean bean;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        if (limit == 0) {
            limit = 10;
        }
        if (checkpoint != null) {
            count = (Integer) checkpoint;
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Reader close(" + jobContext.getBatchStatus() + ", "
                + jobContext.getExitStatus() + "): Transaction ID: " + reg.getTransactionKey());
        bean.close();
        System.out.println("bean.close() called: Transaction ID: " + reg.getTransactionKey());
    }

    @Override
    public Object readItem() throws Exception {
        if (Boolean.valueOf(context.getProperties()
                .getProperty("fail"))) {
            throw new IOException("fail");
        }
        return count++ >= limit ? null : "an item " + count;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return count;
    }

}
