(ns financial-market.process-acquisition-price.logic
  (:require [financial-market.helpers.transactions :as t]))

(defn calculate-acquisition-price [transaction]
  (* (:weighted-average transaction) (:quantity transaction)))

(defn process-acquisition-price
  [transactions]
  (t/update-transactions transactions calculate-acquisition-price :acquisition-price))