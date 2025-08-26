CREATE SCHEMA IF NOT EXISTS test;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS test.users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  correo VARCHAR(255) NOT NULL,
  telefono VARCHAR(50),
  nombres VARCHAR(255) NOT NULL,
  apellidos VARCHAR(255),
  contrasena VARCHAR(255) NOT NULL,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
  fecha_actualizacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION test.set_fecha_actualizacion()
RETURNS TRIGGER AS $$
BEGIN
  NEW.fecha_actualizacion = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_set_fecha_actualizacion ON test.users;
CREATE TRIGGER trg_set_fecha_actualizacion
BEFORE UPDATE ON test.users
FOR EACH ROW
EXECUTE FUNCTION test.set_fecha_actualizacion();