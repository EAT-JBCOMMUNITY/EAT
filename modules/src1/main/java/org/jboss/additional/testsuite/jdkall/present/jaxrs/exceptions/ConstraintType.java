package org.jboss.resteasy.api.validation;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk11/Eap7Plus/jaxrs/src/main/java#7.4.2"})
public class ConstraintType
{
   public enum Type {CLASS, FIELD, PROPERTY, PARAMETER, RETURN_VALUE};
}
