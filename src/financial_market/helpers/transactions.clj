(ns financial-market.helpers.transactions)

(defn update-transactions
  [transactions operation new-key]
  (loop [results []
         transaction (first transactions)
         rest-transactions (rest transactions)]
    ;; helper-values
    (let [value (operation transaction) ;; total-cost value
          new-transaction (assoc transaction new-key value)] ;; updated map, with total-cost key-value
      ;; recursion condition
      (if (empty? rest-transactions)
        ;; process last new-transaction (updated), and return results
        (conj results new-transaction)
        ;; continue recursion, with [results, transaction, rest-transactions] new values
        ;; for the next recursion
        (recur (conj results new-transaction)
               (first rest-transactions)
               (rest rest-transactions))))))
