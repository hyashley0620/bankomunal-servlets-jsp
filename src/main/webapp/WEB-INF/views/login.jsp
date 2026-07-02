<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bankomunal - Iniciar sesion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="top-bar">
    <span>Bankomunal</span>
    <span>Modulo Servlets &amp; JSP</span>
</div>

<div class="container">
    <h1>Iniciar sesion</h1>

    <c:if test="${not empty error}">
        <div class="alert-error">${error}</div>
    </c:if>
    <c:if test="${param.registrado == '1'}">
        <div class="alert-success">Cuenta creada con exito. Ya puedes iniciar sesion.</div>
    </c:if>
    <c:if test="${param.logout == '1'}">
        <div class="alert-success">Sesion cerrada correctamente.</div>
    </c:if>

    <!-- Formulario HTML procesado por LoginServlet via metodo POST -->
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="email">Correo electronico</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Contrasena</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Ingresar</button>
    </form>

    <div class="link-row">
        ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/registro">Registrate aqui</a>
    </div>
</div>

</body>
</html>
