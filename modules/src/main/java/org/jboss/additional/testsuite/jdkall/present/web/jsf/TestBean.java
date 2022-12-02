package org.jboss.additional.testsuite.jdkall.present.web.jsf;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Named(value = "testBean")
@ViewScoped
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.4","modules/testcases/jdkAll/Wildfly/web/src/main/java#27.0.0"})
public class TestBean  implements Serializable {
    private String strValueA;
    private String strValueB;

    @PostConstruct
    public void initialize() {
        setStrValueA("");
        setStrValueB("");
    }

    public String getStrValueA() {
        return strValueA;
    }

    public void setStrValueA(String strValueA) {
        this.strValueA = strValueA;
    }

    public String getStrValueB() {
        return strValueB;
    }

    public void setStrValueB(String strValueB) {
        this.strValueB = strValueB;
    }

    public void testFunc() {
        System.out.println("--- exec testFunc().");
    }

}
