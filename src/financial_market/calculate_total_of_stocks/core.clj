(ns financial_market.calculate-total-of-stocks.core)

(defn calculate-total-of-stocks [operations]
  (rest (reductions (fn [total op]
                      (if (= "buy" (:operation op))
                        (+ total (:quantity op))
                        (- total (:quantity op))))
                    0 operations)))

(def transactions
  [{:operation "buy" :unit-cost 10.00 :quantity 100}
   {:operation "sell" :unit-cost 15.00 :quantity 50}
   {:operation "sell" :unit-cost 15.00 :quantity 50}])

(calculate-total-of-stocks transactions)
