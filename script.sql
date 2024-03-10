DROP DATABASE IF EXISTS comandas_db;
CREATE DATABASE comandas_db;
USE comandas_db;

CREATE TABLE mesas (
    id_mesa INT PRIMARY KEY,
    numero_mesa INT NOT NULL,
    estado VARCHAR(10) NOT NULL CHECK (estado IN ('Ocupada', 'Vacia'))
);

CREATE TABLE productos (
    id_producto INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE comandas (
    id_comanda INT PRIMARY KEY AUTO_INCREMENT,
    id_mesa INT,
    id_producto INT,
    cantidad INT NOT NULL,
    precio_total DECIMAL(10, 2) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

CREATE TABLE historial_mesas (
    id_historial INT PRIMARY KEY AUTO_INCREMENT,
    id_mesa INT,
    estado_anterior VARCHAR(10) NOT NULL CHECK (estado_anterior IN ('Ocupada', 'Vacia')),
    nuevo_estado VARCHAR(10) NOT NULL CHECK (nuevo_estado IN ('Ocupada', 'Vacia')),
    fecha_operacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa)
);


USE comandas_db;

INSERT INTO mesas (id_mesa, numero_mesa, estado) VALUES
    (1, 1, 'Vacia'),
    (2, 2, 'Vacia'),
    (3, 3, 'Vacia'),
    (4, 4, 'Vacia'),
    (5, 5, 'Vacia'),
    (6, 6, 'Vacia'),
    (7, 7, 'Vacia'),
    (8, 8, 'Vacia'),
    (9, 9, 'Vacia'),
    (10, 10, 'Vacia'),
    (11, 11, 'Vacia'),
    (12, 12, 'Vacia'),
    (13, 13, 'Vacia'),
    (14, 14, 'Vacia'),
    (15, 15, 'Vacia'),
    (16, 16, 'Vacia');

INSERT INTO productos (id_producto, nombre, tipo, precio) VALUES
(1, 'Pollo al curry', 'Platos principales', 12.99),
(2, 'Pasta alfredo con champiñones', 'Platos principales', 14.50),
(3, 'Salmón a la parrilla', 'Platos principales', 16.75),
(4, 'Risotto de champiñones', 'Platos principales', 13.99),
(5, 'Tacos de carne', 'Platos principales', 11.25),
(6, 'Rollitos de primavera', 'Entrantes', 8.75),
(7, 'Ceviche de pescado', 'Entrantes', 11.50),
(8, 'Sopa', 'Entrantes', 7.99),
(9, 'Ración de calamares', 'Entrantes', 9.25),
(10, 'Hummus con vegetales', 'Entrantes', 6.99),
(11, 'Agua', 'Bebidas', 2.50),
(12, 'Cafe', 'Bebidas', 3.00),
(13, 'Mojito', 'Bebidas', 7.50),
(14, 'Coca-Cola', 'Bebidas', 2.75),
(15, 'Té helado', 'Bebidas', 3.25),
(16, 'Arroz', 'Guarniciones', 4.50),
(17, 'Ensalada', 'Guarniciones', 5.25),
(18, 'Vegetales asados', 'Guarniciones', 6.75),
(19, 'Quinoa con frutos secos', 'Guarniciones', 7.99),
(20, 'Puré de patatas', 'Guarniciones', 4.25),
(21, 'Tarta de manzana', 'Postres', 8.99),
(22, 'Brownie de chocolate', 'Postres', 6.50),
(23, 'Helado casero', 'Postres', 5.75),
(24, 'Crepes de nutella y platano', 'Postres', 7.25),
(25, 'Tiramisú', 'Postres', 9.50);



