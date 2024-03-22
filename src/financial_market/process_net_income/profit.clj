(ns financial-market.process-net-income.profit)

(defn calculate-profit
  [total-cost acquisition-price]
  (- total-cost acquisition-price))

(defn calculate-profit-after-deductions
  [profit loss-accumulation]
  (max 0 (- profit loss-accumulation)))

(defn calculate-loss-accumulation-after-deductions
  [profit loss-accumulation]
  (max 0 (- loss-accumulation profit)))

(defn calculate-profit-accumulation [profit-accumulation profit-after-deductions]
  (+ profit-accumulation profit-after-deductions))

#_(defn process-profit [total-cost acquisition-price loss-accumulation profit-accumulation]
    (let [profit (calculate-profit total-cost acquisition-price)
          profit-after-deductions (calculate-profit-after-deductions profit loss-accumulation)
          updated-profit-accumulation (calculate-profit-accumulation profit-accumulation profit-after-deductions)]
      updated-profit-accumulation))


(defn process-profit [total-cost acquisition-price loss-accumulation profit-accumulation]
  (-> total-cost
      (calculate-profit acquisition-price)
      (calculate-profit-after-deductions loss-accumulation)
      (calculate-profit-accumulation profit-accumulation)))
