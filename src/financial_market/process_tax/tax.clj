(ns financial-market.process-tax.tax
  (:require
   ;; [clojure.pprint :as pprint]
   [financial-market.helpers.transactions :as h]
   [financial-market.process-net-income.loss :as loss]
   [financial-market.process-net-income.profit :as profit]))

;; predicates
(defn positive-profit? [profit]
  (if (> profit 0)
    true
    false))

(defn tax? [total-cost]
  (let [minimum 20000]
    (if (> total-cost minimum)
      ;; (do
      ;;   (println "yes")
        true
      ;; (do
      ;;   (println "no")
        false)))

;; calculate
(defn tax-profit [profit]
  (let [tax-rate 0.2]
    (h/round 2 (* tax-rate profit))))

(defn calculate-tax
  [{:keys [total-cost net-income profit-acc]} {:keys [last-net-income]}]
  (let [last-net-income (if (> last-net-income 0)
                          last-net-income
                          0)]
   (if (and (tax? total-cost) (positive-profit? net-income))
     (tax-profit (- net-income last-net-income))
     0)))
  ;; (if (and (tax? total-cost) (positive-profit? net-income))
  ;;   (tax-profit net-income)
  ;;   0))
