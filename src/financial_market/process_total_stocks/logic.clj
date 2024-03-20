(ns financial-market.process-total-stocks.logic)

(defn calculate-total-stocks [total transaction]
  (let [quantity (:quantity transaction)
        operation (:operation transaction)]
   (if (= operation "buy")
     (+ total quantity)
     (- total quantity))))

(defn total-stocks-to-map [transaction total-stocks]
  (assoc transaction :total-stocks total-stocks))

(defn process-total-stocks
  [transactions]
  (loop [results           []
         total-stock       0
         transaction       (first transactions)
         rest-transactions (rest transactions)]
    (let [new-stock         (calculate-total-stocks total-stock transaction)
          new-transaction   (total-stocks-to-map transaction new-stock)]
      (if (empty? rest-transactions)
        (conj results new-transaction)
        (recur (conj results new-transaction)
               (identity new-stock)
               (first rest-transactions)
               (rest rest-transactions))))))



(comment
 (def transactions
   [{:operation "buy" :unit-cost 10.00 :quantity 100}
    {:operation "sell" :unit-cost 15.00 :quantity 50}
    {:operation "sell" :unit-cost 15.00 :quantity 50}]))
