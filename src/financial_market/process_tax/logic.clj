(ns financial-market.process-tax.logic
  (:require
   [financial-market.process-tax.tax :as tax]
   [financial-market.helpers.transactions :as h]))

(defn process-operation
  [{:keys [operation results transaction]}]
  (let [{:keys [net-income]} (last results)
        last-net-income net-income
        {:keys [net-income]} transaction
        incomes {:last-net-income last-net-income
                 :net-income net-income}]
   (if (or (h/buy? operation)
           (h/decrease-net-income? incomes))
     (let [tax-map (assoc transaction :tax 0)]
       (conj results tax-map))
     (let [tax-map (assoc transaction :tax (tax/calculate-tax transaction))]
       (conj results tax-map)))))

;; add :tax to transactions-map
(defn process-tax [transactions]
  (loop [results                              []
         {:keys [operation] :as transaction}  (first transactions)
         rest-transactions                    (rest transactions)]
    (let [data {:operation operation
                :results results
                :transaction transaction}]
     (if (empty? rest-transactions)
       (process-operation data)
       (recur (process-operation data)
              (first rest-transactions)
              (rest rest-transactions))))))
