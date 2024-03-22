(ns financial-market.helpers.transactions)

(defn decrease-net-income?
  [{:keys [last-net-income net-income]}]
  (if (> last-net-income net-income)
    true
    false))

(defn buy? [operation]
  (if (= operation "buy")
    true
    false))

(defn loss-acc?
  [loss-acc]
  (if (> loss-acc 0.0)
    true
    false))

(defn sell? [operation] (not (buy? operation)))

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
