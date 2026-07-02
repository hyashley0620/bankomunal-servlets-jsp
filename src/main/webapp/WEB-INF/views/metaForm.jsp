<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bankomunal - ${empty meta ? 'Nueva meta' : 'Editar meta'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="top-bar">
    <span>Bankomunal &middot; Hola, ${sessionScope.usuario.firstName}</span>
    <a href="${pageContext.request.contextPath}/logout">Cerrar sesion</a>
</div>

<div class="container">
    <h1>${empty meta ? 'Nueva meta de ahorro' : 'Editar meta de ahorro'}</h1>

    <!-- Este mismo formulario HTML se usa para crear (action=crear) y
         actualizar (action=actualizar) una meta, procesado por MetasAhorroServlet via POST -->
    <form action="${pageContext.request.contextPath}/metas" method="post">

        <c:choose>
            <c:when test="${empty meta}">
                <input type="hidden" name="action" value="crear">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="actualizar">
                <input type="hidden" name="id" value="${meta.id}">
            </c:otherwise>
        </c:choose>

        <label for="nombre">Nombre de la meta</label>
        <input type="text" id="nombre" name="nombre" value="${meta.nombre}" required>

        <label for="meta">Monto objetivo (COP)</label>
        <input type="number" step="0.01" min="0" id="meta" name="meta" value="${meta.meta}" required>

        <c:if test="${not empty meta}">
            <label for="acumulado">Monto acumulado (COP)</label>
            <input type="number" step="0.01" min="0" id="acumulado" name="acumulado" value="${meta.acumulado}" required>

            <label for="status">Estado</label>
            <select id="status" name="status">
                <option value="active" ${meta.status == 'active' ? 'selected' : ''}>Activa</option>
                <option value="completed" ${meta.status == 'completed' ? 'selected' : ''}>Completada</option>
                <option value="cancelled" ${meta.status == 'cancelled' ? 'selected' : ''}>Cancelada</option>
            </select>
        </c:if>

        <label for="fechaLimite">Fecha limite</label>
        <input type="date" id="fechaLimite" name="fechaLimite" value="${meta.fechaLimite}">

        <button type="submit">Guardar</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/metas">Cancelar</a>
    </form>
</div>

</body>
</html>
