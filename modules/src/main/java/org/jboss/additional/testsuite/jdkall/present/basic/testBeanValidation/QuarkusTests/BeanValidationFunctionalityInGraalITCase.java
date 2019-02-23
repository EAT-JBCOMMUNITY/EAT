package org.jboss.additional.testsuite.jdkall.present.basic.testBeanValidation;

import io.quarkus.test.junit.SubstrateTest;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * Test various Bean Validation operations running in SubstrateVM
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Protean/basic/test-configurations2/src/test/java#1.0.0"})
@SubstrateTest
public class BeanValidationFunctionalityInGraalITCase extends BeanValidationFunctionalityTest {

}
