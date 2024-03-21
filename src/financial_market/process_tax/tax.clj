(ns financial-market.process-tax.tax)

(defn tax-net-income? [net-income]
  (if (> net-income 0)
    true
    false))

(defn tax-net-income [net-income]
  (let [tax-rate 0.2]
    (if (tax-net-income? net-income)
      (* tax-rate net-income)
      0)))

(defn tax? [total-cost]
  (let [minimum 20000]
    (if (> total-cost minimum)
      true
      false)))

(defn calculate-tax
  [{:keys [total-cost net-income] :as transaction}]
  (if (tax? total-cost)
    (tax-net-income net-income)
    0))
