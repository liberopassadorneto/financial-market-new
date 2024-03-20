(ns financial-market.total-stocks.logic)

(defn stocks [total transaction]
  (let [quantity (:quantity transaction)
        operation (:operation transaction)]
   (if (= operation "buy")
     (+ total quantity)
     (- total quantity))))

(defn total-stocks-map [transaction total-stocks]
  (assoc transaction :total-of-stocks total-stocks))

(defn total-stocks
  [transactions]
  (loop [results           []
         total-stock       0
         transaction       (first transactions)
         rest-transactions (rest transactions)]
    (let [new-stock         (stocks total-stock transaction)
          new-transaction   (total-stocks-map transaction new-stock)]
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
