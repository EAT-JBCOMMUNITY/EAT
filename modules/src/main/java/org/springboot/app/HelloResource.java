package org.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jboss.eap.additional.testsuite.annotations.EAT;


@Component
@Path("/hello")
@EAT({"modules/testcases/jdkAll/Eap7plus/spring/buildapp-dir3/src/main/java","modules/testcases/jdkAll/WildflyJakarta/spring/buildapp-dir3/src/main/java"})
public class HelloResource {

    @Autowired
    EchoBean bean;

    @GET
    public String get() {
        return bean.echo("Hello, world!");
    }
}
