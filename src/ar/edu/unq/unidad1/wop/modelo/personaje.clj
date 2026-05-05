(ns ar.edu.unq.unidad1.wop.modelo.personaje)

(defn crear
  ([nombre peso-maximo xp vida]
   (crear nombre peso-maximo xp vida #{}))

  ([nombre peso-maximo xp vida inventario]
   {:nombre      nombre
    :peso-maximo peso-maximo
    :xp          xp
    :vida        vida
    :inventario  inventario}))

(defn peso-actual
  [{:keys [inventario]}]
  (transduce (map :peso) + 0 inventario))

(defn recoger
  "Devuelve un personaje nuevo con `item` agregado al inventario.
   Lanza ex-info de tipo ::mucho-peso si el peso total supera el maximo."
  [personaje item]
  (when (> (+ (peso-actual personaje) (:peso item))
           (:peso-maximo personaje))
    (throw (ex-info (format "El personaje [%s] no puede recoger [%s] porque carga mucho peso ya"
                            (:nombre personaje)
                            (:nombre item))
                    {:type      ::mucho-peso
                     :personaje personaje
                     :item      item})))
  (update personaje :inventario conj item))
