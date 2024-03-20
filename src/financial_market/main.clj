(ns financial-market.main
  (:require
   [financial-market.inputs.digest :refer [digest-data]]
   [financial-market.total-cost.logic :refer [total-cost]]))

(defn -main
  "Recieves a path, or a input file through piping"
  [input]
  (-> input
    (digest-data)
    (total-cost)))

(comment
 (let [path "./data/mock.json"]
   (-> path
    (digest-data)
    (total-cost)))
 ;; => [{:operation "buy", :unit-cost 10.0, :quantity 100, :total-cost 1000.0}
 ;;     {:operation "sell", :unit-cost 15.0, :quantity 50, :total-cost 750.0}
 ;;     {:operation "sell", :unit-cost 15.0, :quantity 50, :total-cost 750.0}]

 (-main "./data/mock.json"))
;; [{k1 v1} {k2 v2}]
;; ({k1 v1} {k2 v2})
