donluisdonluis-- Creamos la tabla para los productos
CREATE TABLE producto (
    idproducto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    imagen VARCHAR(255) -- Guardaremos la ruta o nombre del archivo de imagen
);

-- Insertamos algunos productos de ejemplo
INSERT INTO producto (nombre, descripcion, precio, imagen) VALUES
('Laptop Pro 15"', 'Potente laptop para profesionales y creativos.', 1499.99, 'laptop.jpg'),
('Teclado Mecánico RGB', 'Teclado con switches mecánicos para gaming y escritura.', 120.50, 'teclado.jpg'),
('Mouse Inalámbrico Ergonómico', 'Mouse diseñado para la comodidad durante largas horas de uso.', 79.99, 'mouse.jpg'),
('Monitor UltraWide 34"', 'Monitor curvo para una experiencia inmersiva.', 450.00, 'monitor.jpg'),
('Webcam HD 1080p', 'Cámara web con alta definición para videoconferencias.', 65.75, 'webcam.jpg');

SELECT * FROM PRODUCTO;

-- Tabla para representar el carrito de un cliente
CREATE TABLE carrito (
    idcarrito SERIAL PRIMARY KEY,
    idcliente INT NOT NULL UNIQUE, -- Cada cliente tiene UN carrito activo
    fechacreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idcliente) REFERENCES cliente(idcliente) ON DELETE CASCADE -- Si se borra el cliente, se borra su carrito
);

-- Tabla para guardar los detalles (productos y cantidades de cada carrito)

CREATE TABLE detalle_carrito (
	iddetalle SERIAL PRIMARY KEY,
    idcarrito INT NOT NULL,
    idproducto INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    CONSTRAINT chk_cantidad CHECK (cantidad > 0), -- Asegura que la cantidad sea positiva
    FOREIGN KEY (idcarrito) REFERENCES carrito(idcarrito) ON DELETE CASCADE, -- Si se borra el carrito, se borran sus detalles
    FOREIGN KEY (idproducto) REFERENCES producto(idproducto) ON DELETE CASCADE, -- Si se borra el producto, se elimina de los carritos
    UNIQUE (idcarrito, idproducto) -- Un producto solo puede aparecer una vez por carrito (se actualiza cantidad)
);
