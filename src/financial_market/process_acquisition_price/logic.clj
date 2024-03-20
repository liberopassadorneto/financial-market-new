(ns financial-market.process-acquisition-cost.logic
  (:require [financial-market.helpers.transactions :as t]))

(defn calculate-acquisition-cost [transaction]
  (* (:weighted-average transaction) (:quantity transaction)))

(defn process-acquisition-cost
  [transactions]
  (t/update-transactions transactions calculate-acquisition-cost :acquisition-cost))