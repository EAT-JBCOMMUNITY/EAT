package org.jboss.additional.testsuite.jdkall.present.basic.testBeanValidation;

import org.jboss.shamrock.test.junit.SubstrateTest;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * Test various Bean Validation operations running in SubstrateVM
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Protean/basic/test-configurations/src/test/java"})
@SubstrateTest
public class BeanValidationFunctionalityInGraalITCase extends BeanValidationFunctionalityTest {

}
