(ns financial_market.add-total-of-stocks-to-map.core)

(defn add-total-of-stocks-to-map [transaction total-of-stocks]
  (assoc transaction :total-of-stocks total-of-stocks))

;; Example Transactions and their respective computed total-of-stocks
(def transactions
  [{:operation "buy" :unit-cost 10.00 :quantity 100}
   {:operation "sell" :unit-cost 15.00 :quantity 50}
   {:operation "sell" :unit-cost 15.00 :quantity 50}])

(def total-of-stocks-for-each [100 50 0])

(map add-total-of-stocks-to-map transactions total-of-stocks-for-each)
