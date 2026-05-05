(ns ar.edu.unq.unidad1.wop.service.personaje
  "Coordina la capa de modelo y persistencia. Cada operacion se ejecuta
   dentro de una transaccion para que un fallo deshaga los cambios."
  (:require [ar.edu.unq.unidad1.wop.dao.personaje :as dao]
            [next.jdbc :as jdbc]))

(defn guardar!
  [ds personaje]
  (jdbc/with-transaction [tx ds]
    (dao/guardar! tx personaje)))

(defn recuperar
  [ds nombre]
  (jdbc/with-transaction [tx ds]
    (dao/recuperar tx nombre)))

(defn eliminar!
  [ds personaje]
  (jdbc/with-transaction [tx ds]
    (dao/eliminar! tx personaje)))
