package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.provider.bean;

import java.util.Collection;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#27.0.0.Alpha4"})
public interface ServiceProviderRetriever {

    Collection<String> getProviders();
}
