<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: luisflores
  Date: 7/10/25
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesion</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h4>Iniciar Sesión</h4>
                </div>
                <div class="card-body">
                    <form action="Controlador" method="post">
                        <%-- Campo oculto para la acción de login --%>
                        <input type="hidden" name="accion" value="login">

                        <div class="form-group">
                            <label for="email">Correo Electrónico</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Contraseña</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>

                        <button type="submit" class="btn btn-primary btn-block">Ingresar</button>
                    </form>
                </div>
                <div class="card-footer text-center">
                    <p>¿No tienes una cuenta? <a href="Controlador?accion=verRegistro">Regístrate aquí</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
