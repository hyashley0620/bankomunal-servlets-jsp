<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bankomunal - Crear cuenta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="top-bar">
    <span>Bankomunal</span>
    <span>Modulo Servlets &amp; JSP</span>
</div>

<div class="container">
    <h1>Crear cuenta</h1>

    <c:if test="${not empty error}">
        <div class="alert-error">${error}</div>
    </c:if>

    <!-- Formulario HTML procesado por RegistroServlet via metodo POST -->
    <form action="${pageContext.request.contextPath}/registro" method="post">

        <label for="firstName">Nombres</label>
        <input type="text" id="firstName" name="firstName" required>

        <label for="lastName">Apellidos</label>
        <input type="text" id="lastName" name="lastName" required>

        <label for="email">Correo electronico</label>
        <input type="email" id="email" name="email" required>

        <label for="tipoDocumento">Tipo de documento</label>
        <select id="tipoDocumento" name="tipoDocumento">
            <option value="CC">Cedula de ciudadania</option>
            <option value="CE">Cedula de extranjeria</option>
            <option value="TI">Tarjeta de identidad</option>
        </select>

        <label for="identificationNumber">Numero de documento</label>
        <input type="text" id="identificationNumber" name="identificationNumber">

        <label for="phone">Telefono</label>
        <input type="text" id="phone" name="phone">

        <label for="password">Contrasena</label>
        <input type="password" id="password" name="password" minlength="8" required>

        <label for="confirmPassword">Confirmar contrasena</label>
        <input type="password" id="confirmPassword" name="confirmPassword" minlength="8" required>

        <button type="submit">Registrarme</button>
    </form>

    <div class="link-row">
        ¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesion</a>
    </div>
</div>

</body>
</html>
