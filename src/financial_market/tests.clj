(ns financial-market.tests
  (:require
   [hyperfiddle.rcf :refer [tests]]
   [financial-market.core :refer [debug-process]]))

(tests
 "net-income"
 (map :net-income (debug-process "./data/mock.json"))  := [0 250.0 500.0]
 (map :net-income (debug-process "./data/mock2.json")) := [0 -25000.00 5000.00]
 (map :net-income (debug-process "./data/mock7.json")) := [0 400000.0 400000.0 700000.0]

 "tax"
 (map :tax (debug-process "./data/mock.json"))  := [0 0 0]
 (map :tax (debug-process "./data/mock2.json")) := [0 0 1000.00]
 (map :tax (debug-process "./data/mock3.json")) := [0 0 0]
 (map :tax (debug-process "./data/mock4.json")) := [0 0 0 10000.00]
 (map :tax (debug-process "./data/mock5.json")) := [0 0 0 0 3000.00]
 (map :tax (debug-process "./data/mock6.json")) := [0 0 0 0 3000.00 0 0 3700.00 0])
 ;; (map :tax (debug-process "./data/mock7.json")) := [0 80000.00 0 60000.00])


;; --- Contents:
;;   :acquisition-price = 100000.0
;;   :total-stocks = 5000
;;   :total-cost = 75000.0
;;   :operation = "sell"
;;   :tax = 0
;;   :net-income = -10000.0
;;   :loss-acc = 25000.0
;;   :unit-cost = 15.0
;;   :quantity = 5000
;;   :weighted-average = 20.0
;;   :profit-acc = 15000.0


;; --- Contents:
;;   :acquisition-price = 87000.0
;;   :total-stocks = 650
;;   :total-cost = 130500.0
;;   :operation = "sell"
;;   :tax = 6700.0
;;   :net-income = 33500.0
;;   :loss-acc = 0.0
;;   :unit-cost = 30.0
;;   :quantity = 4350
;;   :weighted-average = 20.0
;;   :profit-acc = 33500.0
