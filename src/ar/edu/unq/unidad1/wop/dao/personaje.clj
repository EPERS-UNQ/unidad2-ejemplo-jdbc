(ns ar.edu.unq.unidad1.wop.dao.personaje
  "Operaciones CRUD sobre personajes."
  (:require [honey.sql :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(defn guardar!
  "Persiste un personaje (sin inventario)."
  [ds {:keys [nombre peso-maximo xp vida]}]
  (jdbc/execute-one!
    ds
    (sql/format {:insert-into :personaje
                 :values      [{:nombre      nombre
                                :peso-maximo peso-maximo
                                :xp          xp
                                :vida        vida}]})))

(defn recuperar
  "Busca un personaje por nombre. Devuelve nil si no existe."
  [ds nombre]
  (when-let [fila (jdbc/execute-one!
                    ds
                    (sql/format {:select [:peso-maximo :xp :vida]
                                 :from   [:personaje]
                                 :where  [:= :nombre nombre]})
                    {:builder-fn rs/as-unqualified-kebab-maps})]
    (assoc fila :nombre nombre :inventario #{})))

(defn eliminar!
  "Elimina un personaje por su nombre."
  [ds {:keys [nombre]}]
  (jdbc/execute-one!
    ds
    (sql/format {:delete-from :personaje
                 :where       [:= :nombre nombre]})))
