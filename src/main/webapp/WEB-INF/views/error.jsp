<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bankomunal - Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="top-bar"><span>Bankomunal</span></div>
<div class="container">
    <h1>Ha ocurrido un error</h1>
    <p style="color: var(--text-muted);">
        La pagina que buscas no existe o ocurrio un problema al procesar tu solicitud.
    </p>
    <a class="btn" href="${pageContext.request.contextPath}/login">Volver al inicio</a>
</div>
</body>
</html>
