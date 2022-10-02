package org.jboss.additional.testsuite.jdkall.present.web.jsf;

import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Named(value = "testBean")
@ViewScoped
@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/web/src/main/java"})
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
