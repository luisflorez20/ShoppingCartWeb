<%-- Importamos las librerías de JSTL para usar sus etiquetas --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tienda Online</title>
    <%-- Usamos Bootstrap para que se vea un poco mejor sin esfuerzo --%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Catálogo de Productos</h1>
        <%-- Este enlace lleva a la acción "verCarrito" en el Controlador --%>
        <a href="Controlador?accion=verCarrito" class="btn btn-primary">
            Ver Carrito (${not empty sessionScope.carrito ? sessionScope.carrito.size() : 0} items)
        </a>
    </div>

    <div class="row">
        <%-- La magia de JSTL: recorremos la lista de productos que nos envió el Controlador --%>
        <c:forEach var="producto" items="${productos}">
            <div class="col-md-4 mb-4">
                <div class="card">
                        <%-- Asumimos que las imágenes están en una carpeta 'img' dentro de 'webapp' --%>
                    <img src="img/${producto.imagen}" class="card-img-top" alt="${producto.nombre}" style="height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h5 class="card-title">${producto.nombre}</h5>
                        <p class="card-text">${producto.descripcion}</p>
                        <p class="card-text font-weight-bold">
                                <%-- Usamos la etiqueta de formato para mostrar el precio como moneda --%>
                            <fmt:setLocale value="es-PE"/>
                            <fmt:formatNumber value="${producto.precio}" type="currency"/>
                        </p>
                            <%-- Este es el botón clave: llama al Controlador con la acción "agregar" y el ID del producto --%>
                        <a href="Controlador?accion=agregar&id=${producto.id}" class="btn btn-success">Agregar al carrito</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>