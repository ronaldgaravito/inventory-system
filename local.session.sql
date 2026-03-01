 
USE tienda;


CREATE TABLE rol (
    id_rol INT(5) PRIMARY KEY,
    nombre_rol VARCHAR(20) NOT NULL,
    descripcion VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- TABLA USUARIOS

CREATE TABLE usuarios (
    id_usuario VARCHAR(10) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    correo VARCHAR(50),
    id_rol INT(5) NOT NULL,
    estado_usuario CHAR(1) NOT NULL,
    fecha_creacion DATE NOT NULL,

    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;


-- TABLA PRODUCTOS

CREATE TABLE productos (
    id_producto VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(50),
    precio INT NOT NULL,
    estado_producto CHAR(1) NOT NULL
) ENGINE=InnoDB;


-- TABLA INVENTARIO

CREATE TABLE inventario (
    id_producto VARCHAR(10),
    stock_minimo INT NOT NULL,
    stock_maximo INT NOT NULL,
    stock_actual INT NOT NULL,
    estado_inventario CHAR(1) NOT NULL,
    fecha DATE NOT NULL,

    PRIMARY KEY (id_producto),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;


-- TABLA REPRESENTANTE LEGAL

CREATE TABLE rep_legal (
    id_rep_legal VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    numero_telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(30) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    estado_rep_legal CHAR(1) NOT NULL,
    fecha DATE NOT NULL
) ENGINE=InnoDB;


-- TABLA PROVEEDORES

CREATE TABLE proveedores (
    id_proveedor VARCHAR(10) PRIMARY KEY,
    nombre_proveedor VARCHAR(50) NOT NULL,
    estado_proveedores CHAR(1) NOT NULL,
    fecha DATE NOT NULL,
    id_rep_legal VARCHAR(10),

    FOREIGN KEY (id_rep_legal) REFERENCES rep_legal(id_rep_legal)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB;


-- TABLA COMPRA

CREATE TABLE compra (
    id_compra VARCHAR(10) PRIMARY KEY,
    id_proveedor VARCHAR(10) NOT NULL,
    total_compra INT NOT NULL,
    fecha DATE NOT NULL,

    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;


-- TABLA DETALLE COMPRA

CREATE TABLE detalle_compra (
    id_detalle VARCHAR(10) PRIMARY KEY,
    id_compra VARCHAR(10) NOT NULL,
    id_producto VARCHAR(10) NOT NULL,
    cantidad INT NOT NULL,
    subtotal INT NOT NULL,

    FOREIGN KEY (id_compra) REFERENCES compra(id_compra)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;


-- TABLA CLIENTES

CREATE TABLE clientes (
    id_cliente VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(30),
    apellido VARCHAR(30),
    correo VARCHAR(50),
    telefono VARCHAR(15)
) ENGINE=InnoDB;

-- TABLA VENTA

CREATE TABLE venta (
    id_venta VARCHAR(10) PRIMARY KEY,
    id_cliente VARCHAR(10),
    total_venta INT NOT NULL,
    fecha DATE NOT NULL,

    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB;


-- TABLA DETALLE VENTA

CREATE TABLE detalle_venta (
    id_detalle VARCHAR(10) PRIMARY KEY,
    id_venta VARCHAR(10) NOT NULL,
    id_producto VARCHAR(10) NOT NULL,
    subtotal INT NOT NULL,
    cantidad INT NOT NULL,

    FOREIGN KEY (id_venta) REFERENCES venta(id_venta)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;


-- INSERTS BÁSICOS

INSERT INTO rol VALUES
(1, 'Admin', 'Administrador del sistema'),
(2, 'Cliente', 'Usuario del sistema');

INSERT INTO productos VALUES
('P001', 'Arroz Diana 1Kg', 2500, 'A'),
('P002', 'Azucar Manuelita', 1800, 'A'),
('P003', 'Aceite Girasol 1L', 8500, 'A');

INSERT INTO inventario VALUES
('P001', 5, 50, 20, 'A', CURDATE()),
('P002', 5, 50, 30, 'A', CURDATE()),
('P003', 5, 50, 15, 'A', CURDATE());

SELECT * FROM rol;
SELECT * FROM productos;
SELECT * FROM inventario;
