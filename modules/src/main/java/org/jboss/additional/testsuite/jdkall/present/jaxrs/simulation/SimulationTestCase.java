package org.jboss.additional.testsuite.jdkall.present.jaxrs.simulation;

import java.nio.file.Files;
import java.nio.file.Path;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import java.io.File;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class SimulationTestCase {

    private final static String WARNAME = "SimulationTestCase.war";
    
    @Deployment(name = "simulation")
    public static Archive<?> createWar() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(SimulationTestCase.class);
        return war;
    }

    @Test
    @OperateOnDeployment("simulation")
    public void testNpeTempFileCreationException() throws Exception { //General code part verification via simulation for all equivalent usages
        Path file;
        File directory = null;
        if (directory != null) {
            file = Files.createTempFile(directory.toPath(), "test", "txt");
        } else {
            file = Files.createTempFile("test", "txt");
        }
    }

}
