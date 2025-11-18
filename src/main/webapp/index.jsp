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

        <div>
            <a id="btnVerCarrito" href="Controlador?accion=verCarrito" class="btn btn-primary mr-2">
                Ver Carrito (${not empty sessionScope.carrito ? sessionScope.carrito.size() : 0} items)
            </a>


            <%-- Usamos JSTL para mostrar contenido condicionalmente --%>
            <c:choose>
                <c:when test="${not empty sessionScope.clienteLogueado}">
                    <%-- Si hay un cliente logueado --%>
                    <span class="navbar-text mr-3">
                    Bienvenido, ${sessionScope.clienteLogueado.nombres}
                </span>
                    <a href="Controlador?accion=logout" class="btn btn-danger">Cerrar Sesión</a>
                </c:when>
                <c:otherwise>
                    <%-- Si no hay nadie logueado --%>
                    <a href="Controlador?accion=verLogin" class="btn btn-success">Iniciar Sesión</a>
                    <a href="Controlador?accion=verRegistro" class="btn btn-info ml-2">Registrarse</a>
                </c:otherwise>
            </c:choose>
        </div>
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
                        <button class="btn btn-success btnAgregarCarrito" data-id="${producto.id}">Agregar al carrito</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    // Seleccionamos todos los botones "Agregar al carrito"
    const botonesAgregar = document.querySelectorAll('.btnAgregarCarrito');
    // Seleccionamos el botón "Ver Carrito" por su ID
    const btnVerCarrito = document.getElementById('btnVerCarrito');

    // Añadimos un listener a cada botón "Agregar"
    botonesAgregar.forEach(boton => {
        boton.addEventListener('click', function(event) {
            // Obtenemos el ID del producto del atributo data-id
            const idProducto = this.dataset.id;

            // Creamos los datos que enviaremos al servidor
            const formData = new FormData();
            formData.append('accion', 'agregarAjax'); // Usaremos una nueva acción para AJAX
            formData.append('id', idProducto);

            // Usamos la API fetch para enviar la petición POST
            fetch('Controlador', {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    // Verificamos si la respuesta fue exitosa
                    if (!response.ok) {
                        throw new Error('Error en la petición AJAX');
                    }
                    // Convertimos la respuesta (que esperamos sea el nuevo conteo) a texto
                    return response.text();
                })
                .then(nuevoConteo => {
                    // Actualizamos el texto del botón "Ver Carrito"
                    btnVerCarrito.textContent = `Ver Carrito (${nuevoConteo} items)`;
                    // Opcional: Mostrar una pequeña notificación visual al usuario
                    console.log(`Producto ${idProducto} agregado. Nuevo conteo: ${nuevoConteo}`);
                    alert(`Producto agregado. Total en carrito: ${nuevoConteo}`); // Simple alerta
                })
                .catch(error => {
                    console.error('Error al agregar producto:', error);
                    alert('Hubo un error al agregar el producto al carrito.');
                });
        });
    });
</script>

</body>
</html>