CREATE DATABASE happyfeetveterinariaa_;
USE happyfeetveterinaria_;

-- =========== TABLAS DE CATÁLOGOS ===========

CREATE TABLE especies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE razas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    especie_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (especie_id) REFERENCES especies(id)
) AUTO_INCREMENT = 1;

CREATE TABLE producto_tipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE evento_tipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE cita_estados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    documento_identidad VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    cargo VARCHAR(100) NOT NULL,
    fecha_contratacion DATE NOT NULL,
    salario DECIMAL(10,2)
) AUTO_INCREMENT = 1;

CREATE TABLE proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    contacto VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(255)
) AUTO_INCREMENT = 1;

CREATE TABLE servicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio_base DECIMAL(10,2) NOT NULL
) AUTO_INCREMENT = 1;

-- =========== TABLAS OPERACIONALES ===========

CREATE TABLE duenos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    documento_identidad VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE mascotas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    raza_id INT NOT NULL,
    fecha_nacimiento DATE,
    sexo ENUM('Macho','Hembra'),
    url_foto VARCHAR(255),
    FOREIGN KEY (raza_id) REFERENCES razas(id)
) AUTO_INCREMENT = 1;

-- Tabla intermedia para dueños y mascotas (muchos a muchos)
CREATE TABLE duenos_mascotas (
    dueno_id INT NOT NULL,
    mascota_id INT NOT NULL,
    PRIMARY KEY (dueno_id, mascota_id),
    FOREIGN KEY (dueno_id) REFERENCES duenos(id),
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id)
) AUTO_INCREMENT = 1;

CREATE TABLE historial_medico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    fecha_evento DATE NOT NULL,
    evento_tipo_id INT NOT NULL,
    descripcion TEXT,
    diagnostico TEXT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
    FOREIGN KEY (evento_tipo_id) REFERENCES evento_tipos(id)
) AUTO_INCREMENT = 1;

-- Tabla de detalle para el historial médico
CREATE TABLE historial_medico_detalle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    historial_medico_id INT NOT NULL,
    producto_id INT,
    servicio_id INT,
    cantidad INT,
    FOREIGN KEY (historial_medico_id) REFERENCES historial_medico(id),
    FOREIGN KEY (producto_id) REFERENCES inventario(id),
    FOREIGN KEY (servicio_id) REFERENCES servicios(id)
) AUTO_INCREMENT = 1;

CREATE TABLE inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255) NOT NULL,
    producto_tipo_id INT NOT NULL,
    descripcion TEXT,
    fabricante VARCHAR(100),
    lote VARCHAR(50),
    cantidad_stock INT NOT NULL,
    stock_minimo INT NOT NULL,
    fecha_vencimiento DATE,
    precio_venta DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (producto_tipo_id) REFERENCES producto_tipos(id)
) AUTO_INCREMENT = 1;

CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    empleado_id INT,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    estado_id INT NOT NULL,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
    FOREIGN KEY (empleado_id) REFERENCES empleados(id),
    FOREIGN KEY (estado_id) REFERENCES cita_estados(id)
) AUTO_INCREMENT = 1;

CREATE TABLE facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dueno_id INT NOT NULL,
    fecha_emision DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (dueno_id) REFERENCES duenos(id)
) AUTO_INCREMENT = 1;

CREATE TABLE items_factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT NOT NULL,
    producto_id INT,
    servicio_id INT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (factura_id) REFERENCES facturas(id),
    FOREIGN KEY (producto_id) REFERENCES inventario(id),
    FOREIGN KEY (servicio_id) REFERENCES servicios(id)
) AUTO_INCREMENT = 1;

CREATE TABLE compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proveedor_id INT NOT NULL,
    fecha_compra DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
) AUTO_INCREMENT = 1;

CREATE TABLE compras_detalle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT NOT NULL,
    inventario_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (compra_id) REFERENCES compras(id),
    FOREIGN KEY (inventario_id) REFERENCES inventario(id)
) AUTO_INCREMENT = 1;

CREATE TABLE citas_servicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cita_id INT NOT NULL,
    servicio_id INT NOT NULL,
    cantidad INT DEFAULT 1,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cita_id) REFERENCES citas(id),
    FOREIGN KEY (servicio_id) REFERENCES servicios(id)
) AUTO_INCREMENT = 1;

select * FROM mascotas;

DROP TABLE duenos_mascotas;
ALTER TABLE mascotas
ADD CONSTRAINT fk_mascotas_dueno
FOREIGN KEY (dueno_id) REFERENCES duenos(id);

ALTER TABLE mascotas
ADD COLUMN dueno_id INT NULL;

UPDATE mascotas
SET dueno_id = id
WHERE id BETWEEN 1 AND 30;