(ns financial-market.process-tax.logic
  (:require
   ;; [clojure.pprint :as pprint]
   [financial-market.helpers.transactions :as h]
   [financial-market.process-tax.tax :as tax]
   [financial-market.helpers.encoding :as encoding]))

(defn process-operation
  [{:keys [operation last-transaction transaction results]}]
  (let [last-net-income     (if (empty? last-transaction)
                              0.0
                              (:net-income last-transaction))
        current-net-income  (:net-income transaction)
        incomes             {:last-net-income last-net-income
                             :net-income current-net-income}]
        ;; _                   (pprint/pprint "---------new-process----------")
        ;; _                   (pprint/pprint {:operation operation
        ;;                                     :incomes incomes
        ;;                                     :transaction transaction})]
   (if (or (h/buy? operation)
           (h/loss-acc? (:loss-acc transaction)))
     (let [tax-map (assoc transaction :tax 0)]
       (conj results tax-map))
     (let [tax-map (assoc transaction :tax (tax/calculate-tax transaction incomes))]
       (conj results tax-map)))))

;; add :tax to transactions-map
(defn process-tax [transactions]
  (loop [results                              []
         last-transaction                     {}
         {:keys [operation] :as transaction}  (first transactions)
         rest-transactions                    (rest transactions)]
    (let [data {:operation          operation
                :last-transaction   last-transaction
                :results            results
                :transaction        transaction}]
     (if (empty? rest-transactions)
       (process-operation data)
       (recur (process-operation data)
              (identity transaction)
              (first rest-transactions)
              (rest rest-transactions))))))

(defn filter-taxes
  [transactions]
  (let [taxes []]
   (-> (for [{:keys [tax]} transactions]
        (conj taxes {:tax tax}))
       (flatten))))
