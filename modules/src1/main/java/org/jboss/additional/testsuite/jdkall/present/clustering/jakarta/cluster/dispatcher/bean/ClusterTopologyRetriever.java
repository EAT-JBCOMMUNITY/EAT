package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.dispatcher.bean;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/clustering/src/main/java"})
public interface ClusterTopologyRetriever {

    ClusterTopology getClusterTopology();
}
