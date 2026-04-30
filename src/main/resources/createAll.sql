CREATE TABLE IF NOT EXISTS personaje (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL UNIQUE,
  pesoMaximo int NOT NULL,
  xp int NOT NULL,
  vida int NOT NULL
);

CREATE TABLE IF NOT EXISTS item (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  peso int NOT NULL,
  UNIQUE(nombre, peso)
);

CREATE TABLE IF NOT EXISTS inventario (
  personaje_nombre VARCHAR(255) NOT NULL,
  item_id int NOT NULL,
  PRIMARY KEY (personaje_nombre, item_id),
  CONSTRAINT fk_inventario_personaje
    FOREIGN KEY (personaje_nombre) REFERENCES personaje(nombre)
    ON DELETE CASCADE,
  CONSTRAINT fk_inventario_item
    FOREIGN KEY (item_id) REFERENCES item(id)
    ON DELETE CASCADE
);