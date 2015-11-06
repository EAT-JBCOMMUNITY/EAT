package org.jboss.test.integration.ws.defaultmethods;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(Arquillian.class)
public class WsTest
{

   @Deployment(name = "jax-ws-test-pojo", testable = false)
   public static WebArchive deploymentWar()
   {
      WebArchive archive = ShrinkWrap.create(WebArchive.class, "jax-ws-test-pojo.war");
      archive.addClass(GreeterSEI.class);
      archive.addClass(GreeterImpl.class);
      archive.addClass(DefaultInterface.class);
      archive.setManifest(new StringAsset("Dependencies: org.jboss.logging"));
      return archive;
   }

   @Test
   @RunAsClient
   public void testPojoDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(
            new URL("http://localhost:8080/jax-ws-test-pojo/GreeterImpl?wsdl"),
            new QName("http://ws.jdk8.qe.redhat.com/", "GreeterImplService"));

      Assert.assertNotNull(greeterService);
      GreeterSEI greeter = greeterService.getPort(GreeterSEI.class);

      //test defautl method implementation
      Assert.assertEquals("Hello, Default", greeter.sayHello());
   }

   @Test
   @RunAsClient
   public void testPojoInheritedDefaultMethod() throws MalformedURLException
   {
      Service greeterService = Service.create(
            new URL("http://localhost:8080/jax-ws-test-pojo/GreeterImpl?wsdl"),
            new QName("http://ws.jdk8.qe.redhat.com/", "GreeterImplService"));

      Assert.assertNotNull(greeterService);
      GreeterSEI greeter = greeterService.getPort(GreeterSEI.class);

      //test defautl method implementation
      Assert.assertEquals("Hi, Default", greeter.sayHi());
   }
}
