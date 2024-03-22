(ns financial-market.core
  (:require
   [financial-market.helpers.encoding :as encoding]
   [financial-market.process-acquisition-price.logic :refer [process-acquisition-price]]
   [financial-market.process-inputs.digest :refer [digest-data]]
   [financial-market.process-net-income.logic :refer [process-net-income]]
   [financial-market.process-tax.logic :refer [filter-taxes process-tax]]
   [financial-market.process-total-cost.logic :refer [process-total-cost]]
   [financial-market.process-total-stocks.logic :refer [process-total-stocks]]
   [financial-market.process-weighted-average.logic :refer [process-weighted-average]])
  (:gen-class))

(defn -main
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
  "`-main` internal processing for catching bugs"
  [input]
  (-> input
      (digest-data)
      (process-total-cost)
      (process-total-stocks)
      (process-weighted-average)
      (process-acquisition-price)
      (process-net-income)
      (process-tax)))

(comment
 (let [path "./data/mock.json"]
   (-main path))

 (let [path "./data/mock2.json"]
   (-main path))

 (let [path "./data/mock3.json"]
  (-main path))

 (let [path "./data/mock4.json"]
   (-main path)))
