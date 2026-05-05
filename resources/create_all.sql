CREATE TABLE IF NOT EXISTS personaje (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL UNIQUE,
  peso_maximo int NOT NULL,
  xp int NOT NULL,
  vida int NOT NULL
);