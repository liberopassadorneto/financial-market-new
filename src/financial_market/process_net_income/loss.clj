(ns financial-market.process-net-income.loss)

(defn calculate-loss [total-cost acquisition-price]
  (- acquisition-price total-cost))

(defn calculate-loss-accumulation [loss loss-accumulation]
  (+ loss-accumulation loss))

#_(defn process-loss [total-cost acquisition-price loss-accumulation]
    (let [loss (calculate-loss total-cost acquisition-price)]
      (calculate-loss-accumulation loss-accumulation loss)))


(defn process-loss [total-cost acquisition-price loss-accumulation]
  (-> total-cost
      (calculate-loss acquisition-price)
      (calculate-loss-accumulation loss-accumulation)))
