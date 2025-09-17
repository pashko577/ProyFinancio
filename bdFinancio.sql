
-- Tabla de usuarios
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
delete from usuarios where id_usuario=3
INSERT INTO usuarios (nombre, dni, correo, telefono, rol, contrasena)
VALUES 


-- Tabla de categorías (ej: sueldo, comida, transporte, etc.)
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(10) CHECK (tipo IN ('INGRESO','GASTO')) NOT NULL
);
SELECT * FROM categorias;
ALTER TABLE categorias 
ADD CONSTRAINT uq_categoria UNIQUE (id_usuario, nombre, tipo);


-- Tabla de movimientos (ingresos y gastos)
CREATE TABLE movimientos (
    id_movimiento SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    id_categoria INT REFERENCES categorias(id_categoria),
    monto NUMERIC(12,2) NOT NULL,
	categoria varchar(50),
    descripcion TEXT,
    fecha TIMESTAMP DEFAULT NOW()
);
select*from movimientos m
join categorias c on c.id_categoria = m.id_categoria;

-- Tabla de aportes automáticos a metas
CREATE TABLE aportes (
    id_aporte SERIAL PRIMARY KEY,
    id_meta INT NOT NULL,  -- relación lógica con Mongo (idMeta)
    monto NUMERIC(12,2) NOT NULL,
    fecha TIMESTAMP DEFAULT NOW()
);