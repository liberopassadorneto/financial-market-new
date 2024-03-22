(ns financial-market.core
  (:require
   [financial-market.helpers.encoding :as encoding]
   [financial-market.process-acquisition-price.logic :refer [process-acquisition-price]]
   [financial-market.process-inputs.digest :refer [digest-data]]
   [financial-market.process-net-income.logic :refer [process-net-income]]
   [financial-market.process-tax.logic :refer [filter-taxes process-tax]]
   [financial-market.process-total-cost.logic :refer [process-total-cost]]
   [financial-market.process-total-stocks.logic :refer [process-total-stocks]]
   [financial-market.process-weighted-average.logic :refer [process-weighted-average]]
   [hyperfiddle.rcf :refer [tests]]))

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
      (process-tax)
      (filter-taxes)
      (encoding/clj->json)))

(defn debug-process
  "`main` internal processing for catching bugs"
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
 "net-income"
 (map :net-income (debug-process "./data/mock.json"))  := [0 250.0 500.0]
 (map :net-income (debug-process "./data/mock2.json")) := [0 -25000.00 5000.00]
 ;; (map :net-income (debug-process "./data/mock3.json")) := [0 0 1000]
 ;; (map :net-income (debug-process "./data/mock4.json")) := [0 0 0]
 ;; (map :net-income (debug-process "./data/mock5.json")) := [0 0 0 10000]
 ;; (map :net-income (debug-process "./data/mock6.json")) := [0 0 0 0 3000.00]
 (map :net-income (debug-process "./data/mock7.json")) := [0 400000.0 400000.0 700000.0]

 "tax"
 (map :tax (debug-process "./data/mock.json"))  := [0 0 0]
 (map :tax (debug-process "./data/mock2.json")) := [0 0 1000.00]
 (map :tax (debug-process "./data/mock3.json")) := [0 0 0]
 (map :tax (debug-process "./data/mock4.json")) := [0 0 0 10000.00]
 (map :tax (debug-process "./data/mock5.json")) := [0 0 0 3000.00]
 (map :tax (debug-process "./data/mock6.json")) := [0 0 0 0 3000.00 0 0 3700.00 0]
 (map :tax (debug-process "./data/mock7.json")) := [0 80000.00 0 60000.00])

(comment
 (let [path "./data/mock.json"]
   (main path))

 (let [path "./data/mock2.json"]
   (main path))

 (let [path "./data/mock3.json"]
  (main path))

 (let [path "./data/mock4.json"]
   (main path)))
