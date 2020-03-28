CREATE TABLE IF NOT EXISTS personaje (
  id int auto_increment NOT NULL ,
  nombre VARCHAR(255) NOT NULL UNIQUE,
  pesoMaximo int NOT NULL,
  xp int NOT NULL,
  vida int NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = InnoDB;