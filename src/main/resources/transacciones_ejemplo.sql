-- Primero, limpiamos la tabla para empezar sin datos
DELETE FROM personaje;

-- Iniciamos la primera transacción a la que haremos rollback
BEGIN;

-- Insertamos a Merry y Pippin en su estado inicial
INSERT INTO personaje (nombre, pesoMaximo, xp, vida)
VALUES ('Merry', 10, 0, 100);

INSERT INTO personaje (nombre, pesoMaximo, xp, vida) 
VALUES ('Pippin', 8, 0, 100);

SELECT * FROM personaje;

-- Por las barbas de Gandalf! Los Uruk-hai los capturaron y se los llevaron a Isengard! (vamos a sacarles vida)
UPDATE personaje 
SET vida = 50 
WHERE nombre IN ('Merry', 'Pippin');

-- Veamos el estado después del ataque
SELECT * FROM personaje;

-- ¡Pero las cosas no pasaron así! Hagamos ROLLBACK para deshacer estos cambios
ROLLBACK;

-- Veamos que no hay registros después del ROLLBACK
SELECT * FROM personaje;

-- Ahora iniciamos una nueva transacción
BEGIN;

-- Insertamos a Merry y Pippin en su encuentro con Bárbol
INSERT INTO personaje (nombre, pesoMaximo, xp, vida) 
VALUES ('Merry', 12, 100, 100);

INSERT INTO personaje (nombre, pesoMaximo, xp, vida) 
VALUES ('Pippin', 10, 100, 100);

-- Insertamos a Bárbol y Ramaviva
INSERT INTO personaje (nombre, pesoMaximo, xp, vida) 
VALUES ('Barbol', 1000, 5000, 1000);

INSERT INTO personaje (nombre, pesoMaximo, xp, vida) 
VALUES ('Ramaviva', 800, 4000, 900);

-- Los hobbits beben agua de ent y se hacen más fuertes
UPDATE personaje 
SET pesoMaximo = pesoMaximo + 5,
    xp = xp + 200
WHERE nombre IN ('Merry', 'Pippin');

-- Veamos el estado final antes del COMMIT
SELECT * FROM personaje;

-- ¡Asi pasaron las cosas posta! Confirmamos los cambios
COMMIT;

-- Veamos que los cambios persisten después del COMMIT
SELECT * FROM personaje; 