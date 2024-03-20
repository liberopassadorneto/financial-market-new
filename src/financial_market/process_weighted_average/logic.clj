(ns financial-market.process-weighted-average.logic)

(defn f [total-of-stocks current-weighted-average]
  (* total-of-stocks current-weighted-average))

(defn g [total-cost]
  total-cost)

(defn h [total-of-stocks]
  total-of-stocks)

(defn calculate-new-weighted-average
  [current-weighted-average transaction]
  (let [f-value (f (h (:total-stocks transaction)) current-weighted-average)
        g-value (g (:total-cost transaction))
        h-value (h (:total-stocks transaction))]
    (if (= (:operation transaction) "buy")
      (/ (+ f-value g-value) h-value)
      current-weighted-average)))

(defn add-weighted-average-to-transaction [transaction weighted-average]
  (assoc transaction :weighted-average weighted-average))

(defn process-weighted-average
  [transactions]
  (loop [results           []
         weighted-average  0
         transaction       (first transactions)
         rest-transactions (rest transactions)]
    (let [new-weighted-average         (calculate-new-weighted-average weighted-average transaction)
          new-transaction   (add-weighted-average-to-transaction transaction new-weighted-average)]
      (if (empty? rest-transactions)
        (conj results new-transaction)
        (recur (conj results new-transaction)
               (identity new-weighted-average)
               (first rest-transactions)
               (rest rest-transactions))))))