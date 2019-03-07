package org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation.custom;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/basic/src/main/java","modules/testcases/jdkAll/Eap72x/basic/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/basic/src/main/java","modules/testcases/jdkAll/Protean/basic/test-configurations/src/main/java#0.9.1**0.9.1","modules/testcases/jdkAll/Protean/basic/test-configurations2/src/main/java#1.0.0"})
@MyCustomConstraint
public class MyOtherBean {

    private String name;

    public MyOtherBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
