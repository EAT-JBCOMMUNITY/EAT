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

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.concurrent.TimeUnit.SECONDS;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.HttpClients;
import org.jboss.as.test.integration.common.HttpRequest;
import org.junit.Ignore;

@RunAsClient
@RunWith(Arquillian.class)
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/web/src/main/java","modules/testcases/jdkAll/Eap7/web/src/main/java"})
public class CookieHeaderServletTestCase {

    public static final String DEPLOYMENT = "cookieHeaderServlet.war";
    public static final String DEPLOYMENT2 = "cookieHeaderServlet2.war";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT);
        war.addClass(CookieHeaderServlet.class);
        return war;
    }
    
    @Deployment(name = DEPLOYMENT2)
    public static Archive<?> getDeployment2() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT2);
        war.addClass(CookieHeaderServlet2.class);
        return war;
    }

    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void cookieHeaderTest(@ArquillianResource URL url) throws Exception {
        URL testURL = new URL(url.toString() + "cookieHeaderServlet");

        final HttpGet request = new HttpGet(testURL.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        response = httpClient.execute(request);
        Assert.assertTrue("Wrong Set-Cookie header format.", response.getFirstHeader("Set-Cookie").getValue().contains("\"example cookie\""));
        IOUtils.closeQuietly(response);
        httpClient.close();

    }
    
    @Test
    @Ignore
    @OperateOnDeployment(DEPLOYMENT2)
    public void cookieHeaderCommaSeparatorTest(@ArquillianResource URL url2) throws Exception {
        URL testURL = new URL(url2.toString() + "cookieHeaderServlet2");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpGet request = new HttpGet(testURL.toString());
        
        CloseableHttpResponse response = null;
        response = httpClient.execute(request);
        response = httpClient.execute(request);
        Assert.assertTrue("The cookie length should be 2.", response.getFirstHeader("cookies.length").getValue().compareTo("2")==0);
        Assert.assertTrue("The cookie value1 should be example_cookie.", response.getFirstHeader("cookies.value1").getValue().compareTo("example_cookie")==0);
        Assert.assertTrue("The cookie value2 should be example2_cookie.", response.getFirstHeader("cookies.value2").getValue().compareTo("example2_cookie")==0);
        IOUtils.closeQuietly(response);
        httpClient.close();

    }
}
