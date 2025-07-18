/*
 * JBoss, Home of Professional Open Source.
 * Copyleft 2017, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.additional.testsuite.jdkall.present.web.servlet.headers;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.net.URL;


@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/web/src/main/java","modules/testcases/jdkAll/Wildfly/web/src/main/java#19.0.0.Final*27.0.0.Beta2","modules/testcases/jdkAll/Eap72x-Proposed/web/src/main/java#7.2.5","modules/testcases/jdkAll/Eap72x/web/src/main/java#7.2.5","modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.3.0.CD18","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#27.0.0.Alpha4*27.0.0.Beta2"})
public class NettyHeaderServletTestCase {

    public static final String DEPLOYMENT = "cookieHeaderServlet.war";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT);
        war.addClass(CookieHeaderServlet.class);
        return war;
    }
 
    
    @OperateOnDeployment(DEPLOYMENT)
    @Test
    public void headerNettyRequestTest(@ArquillianResource URL url) throws Exception {
        URL testURL = new URL(url.toString() + "cookieHeaderServlet");

        HttpRequest request = new DefaultFullHttpRequest(
        HttpVersion.HTTP_1_1, HttpMethod.GET, testURL.toString());
        HttpHeaders headers = request.headers();
        try {
            headers.set("Transfer-Encoding ", "chunked");
            fail("Should not go here...");
        }catch(Exception e){
            assertTrue("Header should be prohibited", (e.getMessage().contains("a header name cannot contain the following prohibited characters") || e.getMessage().contains("a header name can only contain")));
        }

    }
    
}

