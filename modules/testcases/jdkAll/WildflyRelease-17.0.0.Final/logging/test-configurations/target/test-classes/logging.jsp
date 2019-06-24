<%--
  ~ JBoss, Home of Professional Open Source.
  ~ Copyright 2014, Red Hat, Inc., and individual contributors
  ~ as indicated by the @author tags. See the copyright.txt file in the
  ~ distribution for a full listing of individual contributors.
  ~
  ~ This is free software; you can redistribute it and/or modify it
  ~ under the terms of the GNU Lesser General Public License as
  ~ published by the Free Software Foundation; either version 2.1 of
  ~ the License, or (at your option) any later version.
  ~
  ~ This software is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  ~ Lesser General Public License for more details.
  ~
  ~ You should have received a copy of the GNU Lesser General Public
  ~ License along with this software; if not, write to the Free
  ~ Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  ~ 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  --%>

<%-- @EapAdditionalTestsuite({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Wildfly/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap71x/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap71x-Proposed/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap72x/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap72x-Proposed/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap7/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap70x/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap70x-Proposed/logging/test-configurations/src/test/resources","modules/testcases/jdkAll/Eap7.1.0.Beta/logging/test-configurations/src/test/resources"}) --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.logging.*"%>
<%!
    private Logger logger = Logger.getLogger("org.jboss.as.logging.test");
%>
<html>
<head>
    <title>Test Logging JSP</title>
</head>
<body>
<h1>Hello World!</h1>
<h3><%=LogManager.getLogManager()%></h3>
<h3><%=logger%></h3>
<h3><%=logger.getClass().getClassLoader()%></h3>
</body>
<%
    logger.log(Level.INFO, "Logging from JSP");
%>
</html>
