<%-- @EAT({"modules/testcases/jdkAll/Wildfly/basic/test-configurations/src/test/resources#17.0.0.Final","modules/testcases/jdkAll/WildflyJakarta/basic/test-configurations/src/test/resources","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/basic/test-configurations/src/test/resources","modules/testcases/jdkAll/ServerBeta/basic/test-configurations/src/test/resources#17.0.0.Final","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/basic/test-configurations/src/test/resources","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/basic/test-configurations/src/test/resources"}) --%>
<%
    session.setAttribute("v1", new Double(1));
    double v1 = (Double) session.getAttribute("v1");
    out.write(String.valueOf(v1));
%>
