<%--
  Created by IntelliJ IDEA.
  User: luisflores
  Date: 6/10/25
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- Establecemos el locale para el formato de moneda --%>
<fmt:setLocale value="es-PE"/>

<!DOCTYPE html>
<html>
<head>
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h1>Tu Carrito de Compras</h1>
    <hr>

    <%-- Si el carrito está vacío, mostramos un mensaje --%>
    <c:if test="${empty sessionScope.carrito}">
        <div class="alert alert-warning" role="alert">
            Tu carrito está vacío. <a href="Controlador?accion=listar">Vuelve a la tienda</a>.
        </div>
    </c:if>

    <%-- Si el carrito NO está vacío, mostramos la tabla --%>
    <c:if test="${not empty sessionScope.carrito}">
        <table class="table table-hover">
            <thead class="thead-dark">
            <tr>
                <th>Producto</th>
                <th>Precio</th>
                <th class="text-center">Cantidad</th>
                <th>Subtotal</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
                <%-- Inicializamos una variable para el total --%>
            <c:set var="total" value="0" />
            <c:forEach var="item" items="${sessionScope.carrito}">
                <tr>
                    <td>${item.producto.nombre}</td>
                    <td><fmt:formatNumber value="${item.producto.precio}" type="currency"/></td>
                    <td class="text-center">
                            <%-- Un pequeño formulario para cada ítem para actualizar la cantidad --%>
                        <form action="Controlador" method="post" class="form-inline d-inline-block">
                            <input type="hidden" name="accion" value="actualizar">
                            <input type="hidden" name="id" value="${item.producto.id}">
                            <input type="number" name="cantidad" value="${item.cantidad}" class="form-control" style="width: 70px;">
                            <button type="submit" class="btn btn-info btn-sm ml-2">Actualizar</button>
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${item.subtotal}" type="currency"/></td>
                    <td>
                        <a href="Controlador?accion=eliminar&id=${item.producto.id}" class="btn btn-danger btn-sm">Eliminar</a>
                    </td>
                </tr>
                <%-- Sumamos el subtotal de este item al total general --%>
                <c:set var="total" value="${total + item.subtotal}" />
            </c:forEach>
            </tbody>
        </table>

        <div class="row mt-4">
            <div class="col-md-8">
                <a href="Controlador?accion=listar" class="btn btn-secondary">Seguir Comprando</a>
            </div>
            <div class="col-md-4 text-right">
                <h3>Total: <fmt:formatNumber value="${total}" type="currency"/></h3>
                <a href="#" class="btn btn-success btn-block mt-3">Proceder al Pago</a>
            </div>
        </div>
    </c:if>
</div>

</body>
</html>
