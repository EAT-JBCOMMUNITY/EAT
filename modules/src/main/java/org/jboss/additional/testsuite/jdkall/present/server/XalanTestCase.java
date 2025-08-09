/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.server;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/server/src/main/java#39.0.0","modules/testcases/jdkAll/Eap73x/server/src/main/java","modules/testcases/jdkAll/Eap7Plus/server/src/main/java#7.4.12"})
public class XalanTestCase {

    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(XalanTestCase.class);
        return archive;
    }

    @Test
    public void testXalan() throws Exception {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();

        final StringWriter writer = new StringWriter();
        transformer.transform(new StreamSource(new StringReader("<test>\uD83E\uDD2C</test>")), new StreamResult(writer));

        // incorrect: <?xml version="1.0" encoding="UTF-8"?><test>&#55358;&#56620;</test>
        // correct:  <?xml version="1.0" encoding="UTF-8"?><test>&#129324;</test>
        assertTrue(!writer.toString().contains("&#55358;&#56620;"));
    }
}
