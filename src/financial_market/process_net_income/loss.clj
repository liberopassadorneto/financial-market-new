(ns financial-market.process-net-income.loss
  (:require
    [financial-market.helpers.transactions :as h]))

(defn calculate-loss [total-cost acquisition-price]
  (h/round 2 (- acquisition-price total-cost)))

(defn calculate-loss-accumulation [loss loss-accumulation]
  (h/round 2 (+ loss-accumulation loss)))

#_(defn process-loss [total-cost acquisition-price loss-accumulation]
    (let [loss (calculate-loss total-cost acquisition-price)]
      (calculate-loss-accumulation loss-accumulation loss)))


(defn process-loss [total-cost acquisition-price loss-accumulation]
  (-> total-cost
      (calculate-loss acquisition-price)
      (calculate-loss-accumulation loss-accumulation)))
