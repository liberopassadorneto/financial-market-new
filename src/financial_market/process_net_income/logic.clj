(ns financial-market.process-net-income.logic
  (:require
    [financial-market.process-net-income.profit :as profit]
    [financial-market.process-net-income.loss :as loss]))

(defn profit? [total-cost acquisition-price]
  (> total-cost acquisition-price))

(defn calculate-net-income [profit-accumulation loss-accumulation]
  (- profit-accumulation loss-accumulation))

(defn process-net-income [transactions])


