(ns financial-market.process-net-income.logic
  (:require
    [financial-market.process-net-income.profit :as profit]
    [financial-market.process-net-income.loss :as loss]
    [financial-market.helpers.transactions :as h]))

(defn profit?
  [{:keys [total-cost acquisition-price]}]
  (> total-cost acquisition-price))

(defn calculate-net-income
  [{:keys [profit-acc loss-acc]}]
  (h/round 2 (- profit-acc loss-acc)))

 ;; => [{:operation "buy",
 ;;      :unit-cost 10.0,
 ;;      :quantity 100,
 ;;      :total-cost 1000.0,
 ;;      :total-stocks 100,
 ;;      :weighted-average 10.0,
 ;;      :acquisition-price 1000.0}
 ;;      (...)
 ;;      ]


(defn assoc-income
  [{:keys [transaction net-income loss-acc profit-acc]}]
  (assoc transaction
        :net-income net-income
        :loss-acc loss-acc
        :profit-acc profit-acc))

(defn profit-update-map
  [{:keys [total-cost acquisition-price
           loss-acc profit-acc
           transaction results]}]
  (let [profit (profit/calculate-profit total-cost acquisition-price)
        profit-acc (profit/process-profit total-cost acquisition-price loss-acc profit-acc)
        loss-acc (profit/calculate-loss-accumulation-after-deductions profit loss-acc)
        new-transaction-map (assoc-income
                                  {:transaction transaction
                                   :net-income (calculate-net-income {:profit-acc profit-acc
                                                                      :loss-acc   loss-acc})
                                   :loss-acc loss-acc
                                   :profit-acc profit-acc})]
    (conj results new-transaction-map)))

(defn loss-update-map
  [{:keys [total-cost acquisition-price loss-acc
           transaction profit-acc results]}]
  (let [loss-acc (loss/process-loss total-cost acquisition-price loss-acc)
        new-transaction-map (assoc-income
                             {:transaction transaction
                              :net-income (calculate-net-income {:profit-acc profit-acc
                                                                  :loss-acc   loss-acc})
                              :loss-acc loss-acc
                              :profit-acc profit-acc})]
     (conj results new-transaction-map)))


(defn profit-update-map-recur
  [{:keys [total-cost acquisition-price loss-acc
           profit-acc transaction]}]
           ;; rest-transactions results]}]
  (let [profit (profit/calculate-profit total-cost acquisition-price)
        profit-acc (profit/process-profit total-cost acquisition-price loss-acc profit-acc)
        loss-acc (profit/calculate-loss-accumulation-after-deductions profit loss-acc)
        net-income (calculate-net-income {:profit-acc profit-acc
                                          :loss-acc   loss-acc})]
    {:new-transaction-map (assoc-income {:transaction transaction
                                         :net-income net-income
                                         :loss-acc   loss-acc
                                         :profit-acc profit-acc})
     :net-income net-income
     :profit-acc profit-acc
     :loss-acc loss-acc}))

(defn loss-update-map-recur
  [{:keys [total-cost acquisition-price loss-acc
           profit-acc transaction]}]
  (let [loss-acc (loss/process-loss total-cost acquisition-price loss-acc)
        net-income (calculate-net-income {:profit-acc profit-acc
                                          :loss-acc   loss-acc})]
    {:new-transaction-map
     (assoc-income {:transaction transaction
                    :net-income net-income
                    :loss-acc   loss-acc
                    :profit-acc profit-acc})
     :net-income net-income
     :loss-acc loss-acc}))


;; key-value :net-income
(defn process-net-income
  [transactions]
  (loop [results []
         loss-acc 0
         profit-acc 0
         net-income 0
         transaction (first transactions)
         rest-transactions (rest transactions)]
    (let [{:keys [operation total-cost acquisition-price] :as transaction} transaction]
      (if (empty? rest-transactions)
        (if (h/sell? operation)
         (if (profit? {:total-cost        total-cost
                       :acquisition-price acquisition-price})
           ;; update case: profit
           (profit-update-map {:total-cost        total-cost
                               :acquisition-price acquisition-price
                               :loss-acc          loss-acc
                               :profit-acc        profit-acc
                               :transaction       transaction
                               :results           results})
           ;; update case: loss
           (loss-update-map {:total-cost         total-cost
                             :acquisition-price  acquisition-price
                             :loss-acc           loss-acc
                             :transaction        transaction
                             :profit-acc         profit-acc
                             :results            results}))
         (assoc-income {:transaction transaction
                        :net-income net-income
                        :loss-acc   loss-acc
                        :profit-acc profit-acc}))
       (if (h/sell? operation)
         (if (profit? {:total-cost        total-cost
                       :acquisition-price acquisition-price})
           (let [{:keys [net-income new-transaction-map profit-acc loss-acc]}
                 (profit-update-map-recur  {:total-cost        total-cost
                                            :acquisition-price acquisition-price
                                            :loss-acc          loss-acc
                                            :profit-acc        profit-acc
                                            :transaction       transaction})]
              (recur
               (conj results new-transaction-map)
               (identity loss-acc)
               (identity profit-acc)
               (identity net-income)
               (first rest-transactions)
               (rest rest-transactions)))
           (let [{:keys [net-income new-transaction-map
                         loss-acc]}
                 (loss-update-map-recur  {:total-cost        total-cost
                                          :acquisition-price acquisition-price
                                          :loss-acc          loss-acc
                                          :profit-acc        profit-acc
                                          :transaction       transaction})]
             (recur
              (conj results new-transaction-map)
              (identity loss-acc)
              (identity profit-acc)
              (identity net-income)
              (first rest-transactions)
              (rest rest-transactions))))
         (let [new-transaction-map  (assoc-income {:transaction transaction
                                                   :net-income net-income
                                                   :loss-acc   loss-acc
                                                   :profit-acc profit-acc})]
           (recur
             (conj results new-transaction-map)
             (identity loss-acc)
             (identity profit-acc)
             (identity net-income)
             (first rest-transactions)
             (rest rest-transactions))))))))
