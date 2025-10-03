
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    rol VARCHAR(20) CHECK (rol IN ('ADMIN','EMPLEADO')) NOT NULL,
    contrasena VARCHAR(255) NOT NULL, -- encriptada con BCrypt
    fecha_reg TIMESTAMP DEFAULT NOW(),
    id_admin INT REFERENCES usuarios(id_usuario) -- opcional: admin responsable
);

select*from usuarios;

CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(10) CHECK (tipo IN ('INGRESO','GASTO')) NOT NULL,
    CONSTRAINT uq_categoria UNIQUE (id_usuario, nombre, tipo)
);


CREATE TABLE metodopago (
    id_metodopago SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    tipo VARCHAR(50) CHECK (tipo IN (
        'Efectivo','Depósito','Tarjeta de crédito','Tarjeta de débito','Transferencia bancaria','Yape / Plin','Otro'
    ))
);


CREATE TABLE caja (
    id_caja SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE, -- responsable
    nombre VARCHAR(100) NOT NULL,
    fondo NUMERIC(12,2) NOT NULL,
    cierre NUMERIC(12,2) NOT NULL,
    fecha_apertura DATE DEFAULT CURRENT_DATE,
    fecha_cierre DATE
);

select*from caja;



CREATE TABLE movimientos (
    id_movimiento SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    id_categoria INT REFERENCES categorias(id_categoria),
    id_metodopago INT REFERENCES metodopago(id_metodopago),
    monto NUMERIC(12,2) NOT NULL,
    descripcion TEXT,
    fecha TIMESTAMP DEFAULT NOW(),
    creado_por INT REFERENCES usuarios(id_usuario),
    
);
select*from movimientos;

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


CREATE TABLE aportes (
    id_aporte SERIAL PRIMARY KEY,
    id_meta INT REFERENCES metas(id_meta) ON DELETE CASCADE,
    monto NUMERIC(12,2) NOT NULL,
    fecha TIMESTAMP DEFAULT NOW()
);

-- =========================
-- TABLA DE PRESUPUESTOS
-- =========================
CREATE TABLE presupuestos (
    id_presupuesto SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    id_categoria INT REFERENCES categorias(id_categoria) ON DELETE CASCADE,
    monto_maximo NUMERIC(12,2) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);


CREATE TABLE recordatorios (
    id_recordatorio SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_evento TIMESTAMP NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('PAGO','META','OTRO')) NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('PENDIENTE','REALIZADO')) DEFAULT 'PENDIENTE'
);


CREATE TABLE deudas (
    id_deuda SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    monto_total NUMERIC(12,2) NOT NULL,
    monto_pagado NUMERIC(12,2) DEFAULT 0,
    fecha_inicio DATE NOT NULL,
    fecha_vencimiento DATE,
    estado VARCHAR(20) CHECK (estado IN ('PENDIENTE','PAGADA','INCUMPLIDA')) DEFAULT 'PENDIENTE'
);
