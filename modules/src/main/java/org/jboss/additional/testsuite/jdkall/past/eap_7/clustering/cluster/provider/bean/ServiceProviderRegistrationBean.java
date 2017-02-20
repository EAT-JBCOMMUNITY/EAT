package org.jboss.additional.testsuite.jdkall.past.eap_7.clustering.cluster.provider.bean;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.wildfly.clustering.group.Node;
import org.wildfly.clustering.provider.ServiceProviderRegistration;
import org.wildfly.clustering.provider.ServiceProviderRegistry;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@Singleton
@Startup
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Eap70x/clustering/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/clustering/src/main/java"})
@Local(ServiceProviderRegistration.class)
public class ServiceProviderRegistrationBean implements ServiceProviderRegistration<String>, ServiceProviderRegistration.Listener {
    @Resource(lookup = "java:jboss/clustering/providers/server/default")
    private ServiceProviderRegistry<String> factory;
    private ServiceProviderRegistration<String> registration;

    @PostConstruct
    public void init() {
        this.registration = this.factory.register("ServiceProviderRegistrationTestCase", this);
    }

    @PreDestroy
    public void destroy() {
        this.close();
    }

    @Override
    public String getService() {
        return this.registration.getService();
    }

    @Override
    public Set<Node> getProviders() {
        return this.registration.getProviders();
    }

    @Override
    public void close() {
        this.registration.close();
    }

    @Override
    public void providersChanged(Set<Node> nodes) {
        System.out.println(String.format("ProviderRegistration.Listener.providersChanged(%s)", nodes));
    }
}
