# @EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/test2-configurations","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/test2-configurations"}) 
            
java -cp ./../../../../../servers/wildfly/build/target/jbossas/bin/client/jboss-client.jar:./../../../../../servers/wildfly/build/target/jbossas/standalone/deployments/EJBCLIENT-458.jar com.jboss.examples.ejb.HelloClient &> output3.txt
