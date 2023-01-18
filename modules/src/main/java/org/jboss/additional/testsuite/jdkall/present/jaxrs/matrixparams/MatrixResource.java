package org.jboss.additional.testsuite.jdkall.present.jaxrs.matrixparams;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.PathSegment;
import java.util.List;

/**
 *
 * @author gbrey@redhat.com
 *
 */
@Path("/")
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class MatrixResource {
    @Inject
    HelloService helloService;

    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
    }

    @GET
    @Path("/xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
    }

    // Obtain and return just 1 matrix parameter: /rest/matrix;test=foobar;
    @GET
    @Path("/matrix")
    public String getMatrixParamTest(@MatrixParam("test") String test) {
        return String.format("matrix param test = %s%n", test);
    }

    // Obtain and return multiple matrix parameters: /rest/matrix/example;foo=bar;hoge=fuga;test=test;
    @GET
    @Path("/matrix/{var:.+}")
    public String getAllMatrixParamList(@PathParam("var") List<PathSegment> pathSegmentList) {
        StringBuilder sb = new StringBuilder();
        for (PathSegment pathSegment : pathSegmentList) {
            sb.append(String.format("Path: %s, Matrix Params %s<br/>%n", pathSegment.getPath(), pathSegment.getMatrixParameters()));
        }
        return String.format("Matrix Param List:<br/>%n%s%n", sb.toString());
    }

}
