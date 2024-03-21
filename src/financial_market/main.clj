(ns financial-market.main
  (:require
    [financial-market.process-inputs.digest :refer [digest-data]]
    [financial-market.process-total-cost.logic :refer [process-total-cost]]
    [financial-market.process-total-stocks.logic :refer [process-total-stocks]]
    [financial-market.process-weighted-average.logic :refer [process-weighted-average]]
    [financial-market.process-acquisition-price.logic :refer [process-acquisition-price]]))

(defn -main
  "Recieves a path, or a input file through piping"
  [input]
  (-> input
      (digest-data)
      (process-total-cost)
      (process-total-stocks)
      (process-weighted-average)
      (process-acquisition-price)))

(comment
 (let [path "./data/mock.json"]
   (-> path
       (digest-data)
       (process-total-cost)
       (process-total-stocks)
       (process-weighted-average)
       (process-acquisition-price)))

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
