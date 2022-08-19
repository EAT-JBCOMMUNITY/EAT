package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.provider.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

import org.wildfly.clustering.group.Node;
import org.wildfly.clustering.provider.ServiceProviderRegistration;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#27.0.0.Alpha4"})
@Stateless
@Remote(ServiceProviderRetriever.class)
public class ServiceProviderRetrieverBean implements ServiceProviderRetriever {

    @EJB
    private ServiceProviderRegistration<String> registration;

    @Override
    public Collection<String> getProviders() {
        Set<Node> nodes = this.registration.getProviders();
        List<String> result = new ArrayList<>(nodes.size());
        for (Node node: nodes) {
            result.add(node.getName());
        }
        return result;
    }
}
