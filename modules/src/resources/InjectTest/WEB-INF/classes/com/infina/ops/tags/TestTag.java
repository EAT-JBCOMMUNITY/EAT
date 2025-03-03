package com.infina.ops.tags;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.inject.Inject;

import com.infina.ops.EquityServiceBizImpl;

@FacesComponent("com.infina.ops.tags.TestTag")
public class TestTag extends UINamingContainer {

	public String getHello() {
		return this.getHs().getHello();
	}

	@Inject
	private EquityServiceBizImpl hs;

	public EquityServiceBizImpl getHs() {
		return this.hs;
	}
}
