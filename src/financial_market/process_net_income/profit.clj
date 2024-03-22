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


(defn update-map
  [{:keys [total-cost acquisition-price
           loss-acc profit-acc
           transaction results]}]
  (let [profit (profit/calculate-profit total-cost acquisition-price)
        profit-acc (profit/process-profit total-cost acquisition-price loss-acc profit-acc)
        loss-acc (profit/calculate-loss-accumulation-after-deductions profit loss-acc)
        new-transaction-map (assoc transaction
                                  :net-income (calculate-net-income profit-acc loss-acc)
                                  :loss-acc loss-acc
                                  :profit-acc profit-acc)]
    (conj results new-transaction-map)))
