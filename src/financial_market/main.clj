(ns financial-market.main
  (:require
   [financial-market.inputs.digest :refer [digest-data]]
   [financial-market.total-cost.logic :refer [total-cost]]
   [financial-market.total-stocks.logic :refer [total-stocks]]))

(defn -main
  "Recieves a path, or a input file through piping"
  [input]
  (-> input
    (digest-data)
    (total-cost)
    (total-stocks)))

(comment
 (let [path "./data/mock.json"]
   (-> path
    (digest-data)
    (total-cost)))

 (-main "./data/mock.json"))
 ;; => [{:operation "buy",
 ;;      :unit-cost 10.0,
 ;;      :quantity 100,
 ;;      :total-cost 1000.0,
 ;;      :total-of-stocks 100}
 ;;     {:operation "sell",
 ;;      :unit-cost 15.0,
 ;;      :quantity 50,
 ;;      :total-cost 750.0,
 ;;      :total-of-stocks 50}
 ;;     {:operation "sell",
 ;;      :unit-cost 15.0,
 ;;      :quantity 50,
 ;;      :total-cost 750.0,
 ;;      :total-of-stocks 0}])
