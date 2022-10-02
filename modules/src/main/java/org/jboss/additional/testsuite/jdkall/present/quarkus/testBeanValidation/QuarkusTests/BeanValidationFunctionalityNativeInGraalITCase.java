package org.jboss.additional.testsuite.jdkall.present.quarkus.testBeanValidation;

import io.quarkus.test.junit.NativeImageTest;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Test various Bean Validation operations running in SubstrateVM
 */
@EAT({"modules/testcases/jdkAll/Protean/quarkus/quark/test-configurations/src/test/java#1.3.0.Alpha2*999.1.0","modules/testcases/jdkAll/Protean/quarkus/quark2/test-configurations/src/test/java#999.0.0"})
@NativeImageTest
public class BeanValidationFunctionalityNativeInGraalITCase extends BeanValidationFunctionalityTest {

}
