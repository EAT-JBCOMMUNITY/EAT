package org.jboss.additional.testsuite.jdkall.present.web.jsf;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UINamingContainer;

import java.util.ArrayList;
import java.util.List;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#36.0.0","modules/testcases/jdkAll/EapJakarta/web/src/main/java#8.0.9"})
@FacesComponent("customComponent")
public class CustomComponent extends UINamingContainer {	

	public String action() {
		System.out.println(getAttributes().get("customRole"));
		return getAttributes().get("customRole").toString();
	}
}