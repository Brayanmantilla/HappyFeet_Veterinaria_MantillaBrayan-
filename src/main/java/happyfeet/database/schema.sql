CREATE DATABASE happyfeetveterinariaa_;
USE happyfeetveterinaria_;

CREATE TABLE `especies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
);

CREATE TABLE `duenos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_completo` varchar(255) NOT NULL,
  `documento_identidad` varchar(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `documento_identidad` (`documento_identidad`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE `empleados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_completo` varchar(255) NOT NULL,
  `documento_identidad` varchar(20) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `cargo` varchar(100) NOT NULL,
  `fecha_contratacion` date NOT NULL,
  `salario` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `documento_identidad` (`documento_identidad`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE `proveedores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `contacto` varchar(255) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `producto_tipos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
);

CREATE TABLE `servicios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  `precio_base` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `evento_tipos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
);

CREATE TABLE `cita_estados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
);

CREATE TABLE `beneficios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_beneficio` varchar(255) NOT NULL,
  `descripcion` text,
  `puntos_requeridos` int NOT NULL,
  `nivel_minimo` enum('Bronce','Plata','Oro','Diamante') DEFAULT 'Bronce',
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `razas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `especie_id` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `especie_id` (`especie_id`),
  CONSTRAINT `razas_ibfk_1` FOREIGN KEY (`especie_id`) REFERENCES `especies` (`id`)
);

CREATE TABLE `inventario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(255) NOT NULL,
  `producto_tipo_id` int NOT NULL,
  `descripcion` text,
  `fabricante` varchar(100) DEFAULT NULL,
  `lote` varchar(50) DEFAULT NULL,
  `cantidad_stock` int NOT NULL,
  `stock_minimo` int NOT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `producto_tipo_id` (`producto_tipo_id`),
  CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`producto_tipo_id`) REFERENCES `producto_tipos` (`id`)
);

CREATE TABLE `compras` (
  `id` int NOT NULL AUTO_INCREMENT,
  `proveedor_id` int NOT NULL,
  `fecha_compra` datetime NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `proveedor_id` (`proveedor_id`),
  CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedores` (`id`)
);

CREATE TABLE `facturas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dueno_id` int NOT NULL,
  `fecha_emision` datetime NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dueno_id` (`dueno_id`),
  CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`dueno_id`) REFERENCES `duenos` (`id`)
);

CREATE TABLE `club_mascotas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dueno_id` int NOT NULL,
  `fecha_inscripcion` date NOT NULL,
  `puntos_acumulados` int DEFAULT '0',
  `nivel` enum('Bronce','Plata','Oro','Diamante') DEFAULT 'Bronce',
  `estado` enum('Activo','Inactivo','Suspendido') DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  KEY `dueno_id` (`dueno_id`),
  CONSTRAINT `club_mascotas_ibfk_1` FOREIGN KEY (`dueno_id`) REFERENCES `duenos` (`id`)
);

CREATE TABLE `jornadas_vacunacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_jornada` varchar(255) NOT NULL,
  `fecha_jornada` date NOT NULL,
  `lugar` varchar(255) DEFAULT NULL,
  `descripcion` text,
  `empleado_responsable_id` int DEFAULT NULL,
  `estado` enum('Programada','En_Curso','Finalizada','Cancelada') DEFAULT 'Programada',
  PRIMARY KEY (`id`),
  KEY `empleado_responsable_id` (`empleado_responsable_id`),
  CONSTRAINT `jornadas_vacunacion_ibfk_1` FOREIGN KEY (`empleado_responsable_id`) REFERENCES `empleados` (`id`)
);

CREATE TABLE `mascotas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `raza_id` int NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `sexo` enum('Macho','Hembra') DEFAULT NULL,
  `peso` decimal(5,2) DEFAULT NULL,
  `microchip` varchar(50) DEFAULT NULL,
  `url_foto` varchar(255) DEFAULT NULL,
  `dueno_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `raza_id` (`raza_id`),
  KEY `fk_mascotas_dueno` (`dueno_id`),
  CONSTRAINT `fk_mascotas_dueno` FOREIGN KEY (`dueno_id`) REFERENCES `duenos` (`id`),
  CONSTRAINT `mascotas_ibfk_1` FOREIGN KEY (`raza_id`) REFERENCES `razas` (`id`)
);

CREATE TABLE `compras_detalle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `compra_id` int NOT NULL,
  `inventario_id` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `compra_id` (`compra_id`),
  KEY `inventario_id` (`inventario_id`),
  CONSTRAINT `compras_detalle_ibfk_1` FOREIGN KEY (`compra_id`) REFERENCES `compras` (`id`),
  CONSTRAINT `compras_detalle_ibfk_2` FOREIGN KEY (`inventario_id`) REFERENCES `inventario` (`id`)
);

CREATE TABLE `items_factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factura_id` int NOT NULL,
  `producto_id` int DEFAULT NULL,
  `servicio_id` int DEFAULT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `factura_id` (`factura_id`),
  KEY `producto_id` (`producto_id`),
  KEY `servicio_id` (`servicio_id`),
  CONSTRAINT `items_factura_ibfk_1` FOREIGN KEY (`factura_id`) REFERENCES `facturas` (`id`),
  CONSTRAINT `items_factura_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `inventario` (`id`),
  CONSTRAINT `items_factura_ibfk_3` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`)
);

CREATE TABLE `movimientos_puntos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `club_id` int NOT NULL,
  `tipo_movimiento` enum('Ganancia','Canje','Ajuste') NOT NULL,
  `puntos` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_movimiento` date NOT NULL,
  `factura_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `club_id` (`club_id`),
  KEY `factura_id` (`factura_id`),
  CONSTRAINT `movimientos_puntos_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `club_mascotas` (`id`),
  CONSTRAINT `movimientos_puntos_ibfk_2` FOREIGN KEY (`factura_id`) REFERENCES `facturas` (`id`)
);

CREATE TABLE `citas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int NOT NULL,
  `empleado_id` int DEFAULT NULL,
  `fecha_hora` datetime NOT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `estado_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mascota_id` (`mascota_id`),
  KEY `empleado_id` (`empleado_id`),
  KEY `estado_id` (`estado_id`),
  CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`mascota_id`) REFERENCES `mascotas` (`id`),
  CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`empleado_id`) REFERENCES `empleados` (`id`),
  CONSTRAINT `citas_ibfk_3` FOREIGN KEY (`estado_id`) REFERENCES `cita_estados` (`id`)
);

CREATE TABLE `historial_medico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int NOT NULL,
  `fecha_evento` date NOT NULL,
  `evento_tipo_id` int NOT NULL,
  `descripcion` text,
  `diagnostico` text,
  PRIMARY KEY (`id`),
  KEY `mascota_id` (`mascota_id`),
  KEY `evento_tipo_id` (`evento_tipo_id`),
  CONSTRAINT `historial_medico_ibfk_1` FOREIGN KEY (`mascota_id`) REFERENCES `mascotas` (`id`),
  CONSTRAINT `historial_medico_ibfk_2` FOREIGN KEY (`evento_tipo_id`) REFERENCES `evento_tipos` (`id`)
);

CREATE TABLE `adopciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int NOT NULL,
  `dueno_anterior_id` int NOT NULL,
  `dueno_nuevo_id` int DEFAULT NULL,
  `fecha_disponible` date NOT NULL,
  `fecha_adopcion` date DEFAULT NULL,
  `estado` enum('Disponible','En_Proceso','Adoptada','Retirada') DEFAULT 'Disponible',
  `motivo_adopcion` text,
  `observaciones` text,
  PRIMARY KEY (`id`),
  KEY `mascota_id` (`mascota_id`),
  KEY `dueno_anterior_id` (`dueno_anterior_id`),
  KEY `dueno_nuevo_id` (`dueno_nuevo_id`),
  CONSTRAINT `adopciones_ibfk_1` FOREIGN KEY (`mascota_id`) REFERENCES `mascotas` (`id`),
  CONSTRAINT `adopciones_ibfk_2` FOREIGN KEY (`dueno_anterior_id`) REFERENCES `duenos` (`id`),
  CONSTRAINT `adopciones_ibfk_3` FOREIGN KEY (`dueno_nuevo_id`) REFERENCES `duenos` (`id`)
);

CREATE TABLE `jornada_mascotas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `jornada_id` int NOT NULL,
  `mascota_id` int NOT NULL,
  `vacuna_aplicada` varchar(255) DEFAULT NULL,
  `dosis` varchar(50) DEFAULT NULL,
  `fecha_aplicacion` date DEFAULT NULL,
  `observaciones` text,
  PRIMARY KEY (`id`),
  KEY `jornada_id` (`jornada_id`),
  KEY `mascota_id` (`mascota_id`),
  CONSTRAINT `jornada_mascotas_ibfk_1` FOREIGN KEY (`jornada_id`) REFERENCES `jornadas_vacunacion` (`id`),
  CONSTRAINT `jornada_mascotas_ibfk_2` FOREIGN KEY (`mascota_id`) REFERENCES `mascotas` (`id`)
);

CREATE TABLE `citas_servicios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cita_id` int NOT NULL,
  `servicio_id` int NOT NULL,
  `cantidad` int DEFAULT '1',
  `precio` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cita_id` (`cita_id`),
  KEY `servicio_id` (`servicio_id`),
  CONSTRAINT `citas_servicios_ibfk_1` FOREIGN KEY (`cita_id`) REFERENCES `citas` (`id`),
  CONSTRAINT `citas_servicios_ibfk_2` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`)
);

CREATE TABLE `historial_medico_detalle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `historial_medico_id` int NOT NULL,
  `producto_id` int DEFAULT NULL,
  `servicio_id` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `historial_medico_id` (`historial_medico_id`),
  KEY `producto_id` (`producto_id`),
  KEY `servicio_id` (`servicio_id`),
  CONSTRAINT `historial_medico_detalle_ibfk_1` FOREIGN KEY (`historial_medico_id`) REFERENCES `historial_medico` (`id`),
  CONSTRAINT `historial_medico_detalle_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `inventario` (`id`),
  CONSTRAINT `historial_medico_detalle_ibfk_3` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`)
);