(ns ar.edu.unq.unidad1.wop.db
  "Configuracion y bootstrap de la base de datos."
  (:require [clojure.java.io :as io]
            [honey.sql :as sql]
            [next.jdbc :as jdbc]))

;; --- Configuracion ----------------------------------------------------------

(defn db-spec
  "Construye un db-spec a partir de variables de entorno (con valores por defecto)."
  []
  {:dbtype   "postgresql"
   :host     (or (System/getenv "DB_HOST")     "localhost")
   :port     (or (System/getenv "DB_PORT")     5432)
   :dbname   (or (System/getenv "DB_NAME")     "epers")
   :user     (or (System/getenv "DB_USER")     "mateo.difranco")
   :password (or (System/getenv "DB_PASSWORD") "")})

;; --- Bootstrap --------------------------------------------------------------

(defn ensure-database!
  "Crea la base `:dbname` si no existe conectandose a `postgres`."
  [{:keys [dbname] :as spec}]
  (let [admin-ds (jdbc/get-datasource (assoc spec :dbname "postgres"))
        existe?  (jdbc/execute-one!
                   admin-ds
                   (sql/format {:select [[[:inline 1]]]
                                :from   [:pg_database]
                                :where  [:= :datname dbname]}))]
    (when-not existe?
      (jdbc/execute! admin-ds [(str "CREATE DATABASE " dbname)]))))

(defn init-schema!
  "Aplica el script de creacion de tablas (idempotente)."
  [ds]
  (let [schema-script (-> "create_all.sql"
                          io/resource
                          slurp)]
    (jdbc/execute! ds [schema-script])))

(defn datasource
  "Crea (si hace falta) la base, inicializa el schema y devuelve el datasource."
  [spec]
  (ensure-database! spec)
  (let [ds (jdbc/get-datasource spec)]
    (init-schema! ds)
    ds))
