package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.provider.bean;

import java.util.Collection;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/clustering/src/main/java","modules/testcases/jdkAll/WildflyRelease/clustering/src/main/java","modules/testcases/jdkAll/Eap7/clustering/src/main/java"})
public interface ServiceProviderRetriever {

    Collection<String> getProviders();
}
