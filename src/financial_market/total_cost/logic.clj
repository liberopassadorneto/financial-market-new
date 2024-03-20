(ns financial-market.total-cost.logic
  (:require [financial-market.helpers.transactions :as t]))

(defn calculate-total-cost [op]
  (* (:unit-cost op) (:quantity op)))

;; Tail-recursion
(defn total-cost
  [transactions]
  (t/update-transactions transactions calculate-total-cost :total-cost))

(comment
 (def transactions
   [{:operation "buy" :unit-cost 10.00 :quantity 100}
    {:operation "sell" :unit-cost 15.00 :quantity 50}
    {:operation "sell" :unit-cost 15.00 :quantity 50}])

 ;; ------------- Example usage ------------
 (total-cost transactions)
 ;; => [{:operation "buy", :unit-cost 10.0, :quantity 100, :total-cost 1000.0}
 ;;     {:operation "sell", :unit-cost 15.0, :quantity 50, :total-cost 750.0}
 ;;     {:operation "sell", :unit-cost 15.0, :quantity 50, :total-cost 750.0}]

 (take 2
   (total-cost transactions)))
  ;; => ({:operation "buy", :unit-cost 10.0, :quantity 100, :total-cost 1000.0}
  ;;     {:operation "sell", :unit-cost 15.0, :quantity 50, :total-cost 750.0}))
