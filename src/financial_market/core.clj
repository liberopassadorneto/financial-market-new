(ns financial-market.core
  (:require
    [financial-market.process-inputs.digest :refer [digest-data]]
    [financial-market.process-total-cost.logic :refer [process-total-cost]]
    [financial-market.process-total-stocks.logic :refer [process-total-stocks]]
    [financial-market.process-weighted-average.logic :refer [process-weighted-average]]
    [financial-market.process-acquisition-price.logic :refer [process-acquisition-price]]
    [financial-market.process-net-income.logic :refer [process-net-income]]
    [financial-market.process-tax.logic :refer [process-tax]]
    [hyperfiddle.rcf :refer [tests tap %]]))

(defn main
  "Recieves a path, or a input file through piping"
  [input]
  (-> input
      (digest-data)
      (process-total-cost)
      (process-total-stocks)
      (process-weighted-average)
      (process-acquisition-price)
      (process-net-income)
      (process-tax)))

(tests
 "tax"
 (map :tax (main "./data/mock.json")) := [0 0 0]
 (map :tax (main "./data/mock2.json")) := [0 10000 0]
 (map :tax (main "./data/mock3.json")) := [0 0 1000]
 (map :tax (main "./data/mock4.json")) := [0 0 0]
 (map :tax (main "./data/mock5.json")) := [0 0 0 10000])

(comment
 (let [path "./data/mock.json"]
   (main path))

 (let [path "./data/mock2.json"]
   (main path))

 (let [path "./data/mock3.json"]
  (main path))

 (let [path "./data/mock4.json"]
   (main path))

 (main "./data/mock.json"))

 ;; (let [mocks [{:path  "./data/mock.json"
 ;;               :expected [0 0 0]}
 ;;              {:path "./data/mock2.json"
 ;;               :expected [0 10000 0]}
 ;;              {:path "./data/mock3.json"
 ;;               :expected [0 0 1000]}
 ;;              {:path "./data/mock4.json"
 ;;               :expected [0 0 0]}
 ;;              {:path  "./data/mock5.json"
 ;;               :expected [0 0 0 10000]}]]
