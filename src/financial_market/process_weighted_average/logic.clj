(ns financial-market.process-weighted-average.logic
  (:require
   [financial-market.helpers.transactions :as h]))
   ;; [clojure.pprint :as pp]))

(defn f [{:keys [total-stocks weighted-average] :as values}]
  (* total-stocks weighted-average))

(defn g [{:keys [quantity unit-cost]}]
  (* quantity unit-cost))

(defn h [{:keys [total-stocks quantity]}]
  (+ total-stocks quantity))

(defn calculate-new-weighted-average
  [{:keys [current-weighted-average transaction last-transaction]}]
  (let [
        f-value (f {:total-stocks (:total-stocks last-transaction)
                    :weighted-average current-weighted-average})
        g-value (g transaction)
        h-value (h {:total-stocks (:total-stocks last-transaction)
                    :quantity     (:quantity transaction)})]
    (if (= (:operation transaction) "buy")
      (h/round 2 (/ (+ f-value g-value) h-value))
      current-weighted-average)))

(defn add-weighted-average-to-transaction [transaction weighted-average]
  (assoc transaction :weighted-average weighted-average))

(defn process-weighted-average
  [transactions]
  (loop [results            []
         last-transaction  {:total-stocks 0}
         weighted-average  0
         transaction       (first transactions)
         rest-transactions (rest transactions)]
    (let [new-weighted-average (calculate-new-weighted-average {:current-weighted-average weighted-average
                                                                :transaction      transaction
                                                                :last-transaction last-transaction})
          new-transaction   (add-weighted-average-to-transaction transaction new-weighted-average)]
      (if (empty? rest-transactions)
        (conj results new-transaction)
        (recur (conj results new-transaction)
               (identity transaction)
               (identity new-weighted-average)
               (first rest-transactions)
               (rest rest-transactions))))))
