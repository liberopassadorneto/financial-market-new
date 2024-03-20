(ns financial-market.process-net-income.logic)

(defn profit? [total-cost acquisition-price]
  (> total-cost acquisition-price))

(defn calculate-loss [total-cost acquisition-price]
  (- acquisition-price total-cost))

(defn calculate-loss-accumulation [loss-accumulation loss]
  (+ loss-accumulation loss))

(defn process-loss [total-cost acquisition-price loss-accumulation]
  (let [loss (calculate-loss total-cost acquisition-price)]
    (calculate-loss-accumulation loss-accumulation loss)))

(defn calculate-profit [total-cost acquisition-price]
  (- acquisition-price total-cost))

(defn calculate-profit-after-deductions [profit loss-accumulation]
  (max 0 (- profit loss-accumulation)))

(defn calculate-loss-accumulation-after-deductions [profit loss-accumulation]
  (max 0 (- loss-accumulation profit)))

(defn calculate-profit-accumulation [profit-accumulation profit-after-deductions]
  (+ profit-accumulation profit-after-deductions))

(defn process-profit [total-cost acquisition-price loss-accumulation profit-accumulation]
  (let [profit (calculate-profit total-cost acquisition-price)
        profit-after-deductions (calculate-profit-after-deductions profit loss-accumulation)
        loss-accumulation-after-deductions (calculate-loss-accumulation-after-deductions profit loss-accumulation)
        updated-profit-accumulation (calculate-profit-accumulation profit-accumulation profit-after-deductions)]
    [profit-after-deductions loss-accumulation-after-deductions updated-profit-accumulation]))

(defn calculate-net-income [profit-accumulation loss-accumulation]
  (- profit-accumulation loss-accumulation))

(defn process-net-income [transactions]
  (let [profit-accumulation 0
        loss-accumulation 0]
    (loop [results             []
           profit-accumulation profit-accumulation
           loss-accumulation   loss-accumulation
           transactions        transactions]
      (if (empty? transactions)
        results
        (let [transaction               (first transactions)
              rest-transactions         (rest transactions)
              total-cost                (:total-cost transaction)
              acquisition-price         (:acquisition-price transaction)
              [loss-accumulation-after-deductions updated-profit-accumulation]
              (if (profit? total-cost acquisition-price)
                (process-profit total-cost acquisition-price loss-accumulation profit-accumulation)
                (process-loss total-cost acquisition-price loss-accumulation))
              net-income                (calculate-net-income updated-profit-accumulation loss-accumulation-after-deductions)]
          (recur (conj results net-income)
                 updated-profit-accumulation
                 loss-accumulation-after-deductions
                 rest-transactions))))))


