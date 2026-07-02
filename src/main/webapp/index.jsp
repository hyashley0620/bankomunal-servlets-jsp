<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Punto de entrada de la aplicacion: redirige siempre al login.
    response.sendRedirect(request.getContextPath() + "/login");
%>
