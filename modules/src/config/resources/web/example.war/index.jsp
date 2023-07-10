<%
// RequestDispatcher dispatcher = request.getRequestDispatcher("test/example.jsp"); // OK
// RequestDispatcher dispatcher = request.getRequestDispatcher("test//example.jsp"); // OK
// RequestDispatcher dispatcher = request.getRequestDispatcher("/test/example.jsp"); // OK
// RequestDispatcher dispatcher = request.getRequestDispatcher("//test/example.jsp"); // recompile is not triggered even after example.jsp is updated
RequestDispatcher dispatcher = request.getRequestDispatcher("/test//example.jsp"); // recompile is not triggered even after example.jsp is updated
dispatcher.forward(request, response);
%>
