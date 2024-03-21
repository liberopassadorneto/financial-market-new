(ns financial-market.process-net-income.logic
  (:require
    [financial-market.process-net-income.profit :as profit]
    [financial-market.process-net-income.loss :as loss]))

(defn profit? [total-cost acquisition-price]
  (> total-cost acquisition-price))

(defn calculate-net-income [profit-accumulation loss-accumulation]
  (- profit-accumulation loss-accumulation))

 ;; => [{:operation "buy",
 ;;      :unit-cost 10.0,
 ;;      :quantity 100,
 ;;      :total-cost 1000.0,
 ;;      :total-stocks 100,
 ;;      :weighted-average 10.0,
 ;;      :acquisition-price 1000.0}
 ;;      (...)
 ;;      ]

;; key-value :net-income
(defn process-net-income
  [transactions]
  (loop [results []
         loss-acc 0
         profit-acc 0
         net-income 0
         transaction (first transactions)
         rest-transactions (rest transactions)]
    (let [{:keys [operation total-cost acquisition-price]} transaction
          loss-acc (loss/process-loss total-cost acquisition-price loss-acc)]
      (if (empty? rest-transactions)
        (if (= operation "sell")
         (if (profit? total-cost acquisition-price)
           ;; update case: profit
           (let [profit (profit/calculate-profit total-cost acquisition-price)
                 profit-acc (profit/process-profit total-cost acquisition-price loss-acc profit-acc)
                 loss-acc (profit/calculate-loss-accumulation-after-deductions profit loss-acc)
                 new-transaction-map (assoc transaction :net-income (calculate-net-income profit-acc loss-acc))]
             (conj results new-transaction-map))
             
           ;; update case: loss
           (let [loss-acc (loss/process-loss total-cost acquisition-price loss-acc)
                 new-transaction-map (assoc transaction :net-income (calculate-net-income profit-acc loss-acc))]
             (conj results new-transaction-map)))
         (assoc transaction :net-income net-income))
        (if (profit? total-cost acquisition-price)
          (let [profit (profit/calculate-profit total-cost acquisition-price)
                profit-acc (profit/process-profit total-cost acquisition-price loss-acc profit-acc)
                loss-acc (profit/calculate-loss-accumulation-after-deductions profit loss-acc)
                net-income (calculate-net-income profit-acc loss-acc)
                new-transaction-map (assoc transaction :net-income (calculate-net-income profit-acc loss-acc))]
             (recur
              (conj results new-transaction-map)
              (identity loss-acc)
              (identity profit-acc)
              (identity net-income)
              (first rest-transactions)
              (rest rest-transactions)))
          (let [loss-acc (loss/process-loss total-cost acquisition-price loss-acc)
                net-income (calculate-net-income profit-acc loss-acc)
                new-transaction-map (assoc transaction :net-income (calculate-net-income profit-acc loss-acc))]
            (recur
             (conj results new-transaction-map)
             (identity loss-acc)
             (identity profit-acc)
             (identity net-income)
             (first rest-transactions)
             (rest rest-transactions))))))))



