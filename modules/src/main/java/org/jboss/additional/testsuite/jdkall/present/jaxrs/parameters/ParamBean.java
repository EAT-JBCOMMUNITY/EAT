/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.additional.testsuite.jdkall.present.jaxrs.parameters;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ParamBean {
    @FormParam("username")
    String username;

    @FormParam("email")
    String email;

    @HeaderParam("Content-Type")
    String contentType;


    public String dumpData() {
        return "ParamBean{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

}
