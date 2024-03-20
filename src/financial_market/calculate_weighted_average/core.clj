(ns financial_market.calculate-weighted-average.core)

(defn f [total-of-stocks current-weighted-average]
  (* total-of-stocks current-weighted-average))

(defn g [total-cost]
  total-cost)

(defn h [total-of-stocks]
  total-of-stocks)

(defn calculate-new-weighted-average
  [current-weighted-average transaction]
  (let [f-value (f (h (:total-of-stocks transaction)) current-weighted-average)
        g-value (g (:total-cost transaction))
        h-value (h (:total-of-stocks transaction))]
    (if (= (:operation transaction) "buy")
      (/ (+ f-value g-value) h-value)
      current-weighted-average)))

(defn calculate-averages
  [transactions]
  (rest
    (reductions
      (fn [acc transaction]
        (calculate-new-weighted-average acc transaction))
      0
      transactions)))

;; Exemplo de input de transações
(def transactions
  [{:operation "buy" :unit-cost 10.0 :quantity 10000 :total-cost 100000.0 :total-of-stocks 10000}
   {:operation "sell" :unit-cost 2.0 :quantity 5000 :total-cost 10000.0 :total-of-stocks 5000}
   {:operation "sell" :unit-cost 20.0 :quantity 2000 :total-cost 40000.0 :total-of-stocks 3000}
   {:operation "sell" :unit-cost 20.0 :quantity 2000 :total-cost 40000.0 :total-of-stocks 1000}
   {:operation "sell" :unit-cost 25.0 :quantity 1000 :total-cost 25000.0 :total-of-stocks 0}
   {:operation "buy" :unit-cost 20.0 :quantity 10000 :total-cost 200000.0 :total-of-stocks 10000}
   {:operation "sell" :unit-cost 15.0 :quantity 5000 :total-cost 75000.0 :total-of-stocks 5000}
   {:operation "sell" :unit-cost 30.0 :quantity 4350 :total-cost 130500.0 :total-of-stocks 650}
   {:operation "sell" :unit-cost 30.0 :quantity 650 :total-cost 19500.0 :total-of-stocks 0}]
  )

;; Chamar a função e imprimir os resultados
(println (calculate-averages transactions))


