DROP DATABASE IF EXISTS db_hospital;
CREATE DATABASE db_cafeteria;
USE db_cafeteria;

-- Tabla de Roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla de Usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    rol_id BIGINT,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Categorías
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE -- Ejemplo: 'Café', 'Kits', 'Accesorios'
);

-- Productos
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL, -- Siempre DECIMAL para dinero
    stock INT NOT NULL DEFAULT 0,
    imagen_url VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE, -- RF-BE-06: Eliminación lógica
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- Pedidos
CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cliente_nombre VARCHAR(100) NOT NULL,
    celular VARCHAR(20) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    estado ENUM('PENDIENTE', 'EN_PREPARACION', 'ENTREGADO') DEFAULT 'PENDIENTE', -- HU10
    total DECIMAL(10,2) NOT NULL,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Detalle del Pedido
CREATE TABLE detalle_pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);