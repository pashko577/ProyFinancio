-- TABLA DE USUARIOS

CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    rol VARCHAR(20) CHECK (rol IN ('ADMIN','EMPLEADO')) NOT NULL,
    contrasena VARCHAR(255) NOT NULL, -- encriptada con BCrypt
    fecha_reg TIMESTAMP DEFAULT NOW()
);
select*from usuarios;

-- TABLA DE CATEGORÍAS

CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(10) CHECK (tipo IN ('INGRESO','GASTO')) NOT NULL
);
select *from categorias
-- Restricción: cada usuario no puede repetir categoría con el mismo tipo
ALTER TABLE categorias 
ADD CONSTRAINT uq_categoria UNIQUE (id_usuario, nombre, tipo);


-- TABLA DE Cuentas (bancos, efectivo, tarjetas)

CREATE TABLE metodopago (
    id_metodopago SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    tipo VARCHAR(50) CHECK (tipo IN ('Efectivo','Depósito','Tarjeta de crédito','Tarjeta de débito','Transferencia bancaria','Yape / Plin','Otro'))
);
select*from metodopago;

-- TABLA DE MOVIMIENTOS (ingresos y gastos)

CREATE TABLE movimientos (
    id_movimiento SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    id_categoria INT REFERENCES categorias(id_categoria),
	id_metodopago INT REFERENCES metodopago(id_metodopago),
    monto NUMERIC(12,2) NOT NULL,
    categoria VARCHAR(50), -- redundante, pero útil para reportes rápidos
    descripcion TEXT,
    fecha TIMESTAMP DEFAULT NOW(), 
	id_caja INT REFERENCES caja_chica(id_caja)
);


select * from movimientos;


-- TABLA DE METAS FINANCIERAS

CREATE TABLE metas (
    id_meta SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    monto_objetivo NUMERIC(12,2) NOT NULL,
    monto_actual NUMERIC(12,2) DEFAULT 0,
    fecha_inicio DATE DEFAULT CURRENT_DATE,
    fecha_limite DATE,
    estado VARCHAR(20) CHECK (estado IN ('EN_PROGRESO','COMPLETADA','CANCELADA')) DEFAULT 'EN_PROGRESO'
);


-- TABLA DE APORTES A METAS

CREATE TABLE aportes (
    id_aporte SERIAL PRIMARY KEY,
    id_meta INT REFERENCES metas(id_meta) ON DELETE CASCADE,
    monto NUMERIC(12,2) NOT NULL,
    fecha TIMESTAMP DEFAULT NOW()
);


-- TABLA DE PRESUPUESTOS

CREATE TABLE presupuestos (
    id_presupuesto SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    id_categoria INT REFERENCES categorias(id_categoria) ON DELETE CASCADE,
    monto_maximo NUMERIC(12,2) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);


-- TABLA DE RECORDATORIOS

CREATE TABLE recordatorios (
    id_recordatorio SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_evento TIMESTAMP NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('PAGO','META','OTRO')) NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('PENDIENTE','REALIZADO')) DEFAULT 'PENDIENTE'
);


-- TABLA DE DEUDAS / PRÉSTAMOS

CREATE TABLE deudas (
    id_deuda SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL, -- Ej: “Préstamo Banco X” o “Deuda con Juan”
    monto_total NUMERIC(12,2) NOT NULL,
    monto_pagado NUMERIC(12,2) DEFAULT 0,
    fecha_inicio DATE NOT NULL,
    fecha_vencimiento DATE,
    estado VARCHAR(20) CHECK (estado IN ('PENDIENTE','PAGADA','INCUMPLIDA')) DEFAULT 'PENDIENTE'
);


-- TABLA DE AUDITORÍA (seguridad / historial)

/*CREATE TABLE auditoria (
    id_auditoria SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    accion VARCHAR(200) NOT NULL,
    fecha TIMESTAMP DEFAULT NOW(),
    ip_origen VARCHAR(50)
);
*/

-- TABLA DE CAJA CHICA

CREATE TABLE caja_chica (
    id_caja SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE, -- responsable
    nombre VARCHAR(100) NOT NULL,      -- Ej: Caja oficina principal
    monto_inicial NUMERIC(12,2) NOT NULL,
    monto_actual NUMERIC(12,2) NOT NULL,
    fecha_apertura DATE DEFAULT CURRENT_DATE,
    estado VARCHAR(20) CHECK (estado IN ('ABIERTA','CERRADA')) DEFAULT 'ABIERTA'
);


-- TABLA DE MOVIMIENTOS DE CAJA CHICA

CREATE TABLE caja_chica_movimientos (
    id_movimiento SERIAL PRIMARY KEY,
    id_caja INT REFERENCES caja_chica(id_caja) ON DELETE CASCADE,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE, -- quien registra el gasto
    descripcion TEXT NOT NULL,
    monto NUMERIC(12,2) NOT NULL,
    tipo VARCHAR(10) CHECK (tipo IN ('EGRESO','REPOSICION')) NOT NULL,
    fecha TIMESTAMP DEFAULT NOW()
);

-- Ahora sí, vinculamos caja chica con movimientos generales
ALTER TABLE movimientos
    ADD COLUMN id_caja INT REFERENCES caja_chica(id_caja);