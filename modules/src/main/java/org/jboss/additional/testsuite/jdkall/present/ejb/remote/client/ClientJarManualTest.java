package org.jboss.additional.testsuite.jdkall.present.ejb.remote.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@EAT({"modules/testcases/jdkAll/EapJakarta/ejb/src/main/java","modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.9"})
public class ClientJarManualTest {

    private static String result = null;

    @Test 
    public void testServerSuspentionMode() throws Exception {
        try {
            ArrayList<String> commands=new ArrayList<String>();
            commands.add("/bin/bash");
            commands.add("-c");
            commands.add("./startServer.sh");
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process p = pb.start();
            Thread.sleep(10000);
            ArrayList<String> commands2=new ArrayList<String>();
            commands2.add("/bin/bash");
            commands2.add("-c");
            commands2.add("./clientjar.sh");
            ProcessBuilder pb2 = new ProcessBuilder(commands2);
            Process p2 = pb2.start();
            Thread.sleep(5000);
            String content = new String(Files.readAllBytes(Paths.get("output3.txt")), StandardCharsets.UTF_8);

            assertTrue(!content.contains("NoClassDefFoundError"));
            p2.destroy();
            p.destroy();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
