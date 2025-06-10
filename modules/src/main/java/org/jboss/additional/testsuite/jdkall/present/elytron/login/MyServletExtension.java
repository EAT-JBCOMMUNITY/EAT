package org.jboss.additional.testsuite.jdkall.present.elytron.login;

import javax.servlet.ServletContext;

import io.undertow.server.HttpHandler;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.8"})
public class MyServletExtension
        implements ServletExtension {

    private static final HttpHandler myTokenHandler = new MyDummyTokenHandler();

    @Override
    public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
        deploymentInfo.addSecurityWrapper(nextHandler -> exchange -> {
            myTokenHandler.handleRequest(exchange);
            nextHandler.handleRequest(exchange);
        });
    }

}
