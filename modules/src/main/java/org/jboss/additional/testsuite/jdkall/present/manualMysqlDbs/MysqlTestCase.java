/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.manualMysqlDbs;

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
import org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker;
import org.jboss.jca.adapters.jdbc.CheckValidConnectionSQL;
import org.jboss.jca.adapters.jdbc.spi.ValidConnectionChecker;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import java.io.File;
import java.io.FileInputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.net.URL;

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/Eap7Plus/manualMysqlDbs/src/main/java#7.4.11","modules/testcases/jdkAll/WildflyJakarta/manualMysqlDbs/src/main/java#30.0.0"})
public class MysqlTestCase {

    private static final File serverLog = new File(System.getProperty("jboss.dist"), "standalone" + File.separator + "log"
            + File.separator + "server.log");
    
    public static final String DEPLOYMENT = "mysqlServlet.war";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT);
        war.addClass(MySQLValidConnectionChecker.class);
        war.addPackage(CheckValidConnectionSQL.class.getPackage());
        war.addClass(ValidConnectionChecker.class);
        war.addClass(MysqlServlet.class);
        war.addClass(MysqlTestCase.class);
        return war;
    }

    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void mysqlTest(@ArquillianResource URL url) throws Exception {
        URL testURL = new URL(url.toString() + "mysql");

        final HttpGet request = new HttpGet(testURL.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testURL, HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode()); 

            FileInputStream inputStream = new FileInputStream(serverLog.getAbsolutePath());
            try {
                String everything = IOUtils.toString(inputStream);
                Assert.assertTrue("Timeout is not set ...", everything.contains("### 1000"));
            } finally {
                inputStream.close();
            }
        }finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }
    
}
