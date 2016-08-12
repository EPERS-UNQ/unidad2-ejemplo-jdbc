DROP SCHEMA IF EXISTS epers_ejemplo_jdbc;
CREATE SCHEMA epers_ejemplo_jdbc;

USE epers_ejemplo_jdbc;

CREATE TABLE personaje (
  nombre VARCHAR(255) NOT NULL UNIQUE,
  pesoMaximo int NOT NULL,
  xp int NOT NULL,
  vida int NOT NULL,
  PRIMARY KEY (nombre)
)
ENGINE = InnoDB;
