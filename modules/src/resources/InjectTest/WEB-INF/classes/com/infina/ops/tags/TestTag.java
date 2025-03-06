package com.infina.ops.tags;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("com.infina.ops.tags.TestTag")
public class TestTag extends UINamingContainer {

	public String getHello() {
		return "Hello";
	}

        public String getAction() {
		System.out.println(getAttributes().get("customRole"));
		return getAttributes().get("customRole").toString();
	}
}
