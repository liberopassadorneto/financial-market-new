(ns financial-market.process-tax.tax
  (:require
   [clojure.pprint :as pprint]))

;; predicates
(defn positive-net-income? [net-income]
  (if (> net-income 0)
    true
    false))

(defn tax? [total-cost]
  (let [minimum 20000]
    (if (> total-cost minimum)
      true
      false)))

;; calculate
(defn tax-net-income [net-income]
  (let [tax-rate 0.2]
    (* tax-rate net-income)))

(defn calculate-tax
  [{:keys [total-cost net-income]}]
  (do
   (pprint/pprint {:total-cost (if (nil? total-cost) 0.0 total-cost)
                   :net-income net-income})
   (if (and (tax? (if (nil? total-cost) 0.0 total-cost))
            (positive-net-income? net-income))
     (tax-net-income net-income)
     0)))

