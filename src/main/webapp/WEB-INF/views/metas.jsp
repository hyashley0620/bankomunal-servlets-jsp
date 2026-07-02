<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bankomunal - Mis metas de ahorro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="top-bar">
    <span>Bankomunal &middot; Hola, ${sessionScope.usuario.firstName}</span>
    <a href="${pageContext.request.contextPath}/logout">Cerrar sesion</a>
</div>

<div class="container-wide">
    <h1>Mis metas de ahorro</h1>
    <a class="btn" href="${pageContext.request.contextPath}/metas?action=nueva">+ Nueva meta</a>

    <c:choose>
        <c:when test="${empty metas}">
            <p style="color: var(--text-muted); margin-top: 24px;">
                Aun no tienes metas de ahorro registradas. Crea la primera con el boton de arriba.
            </p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Meta</th>
                    <th>Acumulado</th>
                    <th>Avance</th>
                    <th>Fecha limite</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="m" items="${metas}">
                    <tr>
                        <td>${m.nombre}</td>
                        <td><fmt:formatNumber value="${m.meta}" type="currency" currencySymbol="$"/></td>
                        <td><fmt:formatNumber value="${m.acumulado}" type="currency" currencySymbol="$"/></td>
                        <td>
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: ${m.porcentajeAvance}%;"></div>
                            </div>
                            <span style="font-size:0.8rem;color:var(--text-muted);">${m.porcentajeAvance}%</span>
                        </td>
                        <td>${m.fechaLimite}</td>
                        <td>
                            <span class="badge badge-${m.status}">${m.status}</span>
                        </td>
                        <td class="actions">
                            <a class="btn btn-small" href="${pageContext.request.contextPath}/metas?action=editar&id=${m.id}">Editar</a>
                            <form action="${pageContext.request.contextPath}/metas" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="id" value="${m.id}">
                                <button type="submit" class="btn btn-small btn-danger"
                                        onclick="return confirm('¿Eliminar esta meta de ahorro?');">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
