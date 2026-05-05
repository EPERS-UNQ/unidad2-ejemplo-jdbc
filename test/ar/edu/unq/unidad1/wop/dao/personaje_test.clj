(ns ar.edu.unq.unidad1.wop.dao.personaje-test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [ar.edu.unq.unidad1.wop.dao.personaje :as dao]
            [ar.edu.unq.unidad1.wop.db :as db]
            [ar.edu.unq.unidad1.wop.modelo.item :as item]
            [ar.edu.unq.unidad1.wop.modelo.personaje :as personaje]))

(def ^:dynamic *ds* nil)

(defn- with-datasource [t]
  (binding [*ds* (db/datasource (db/db-spec))]
    (t)))

(use-fixtures :once with-datasource)

(defn- maguito []
  (-> (personaje/crear "Maguito" 15 2500 198)
      (personaje/recoger (item/crear "Tunica gris" 1))
      (personaje/recoger (item/crear "Baculo gris" 5))))

(defn- with-personaje [p f]
  (try
    (dao/guardar! *ds* p)
    (f)
    (finally
      (dao/eliminar! *ds* p))))

(deftest al-guardar-y-luego-recuperar-se-obtienen-objetos-similares
  (let [original (maguito)]
    (with-personaje original
      (fn []
        (let [recuperado (dao/recuperar *ds* "Maguito")]
          (testing "los campos persistidos coinciden"
            (is (= (:nombre original)      (:nombre recuperado)))
            (is (= (:peso-maximo original) (:peso-maximo recuperado)))
            (is (= (:vida original)        (:vida recuperado)))
            (is (= (:xp original)          (:xp recuperado))))
          (testing "perdida de identidad: el inventario no se persiste"
            (is (not= original recuperado))))))))
