(ns financial_market.add-total-cost-to-map.core-test
  (:require [clojure.test :refer :all]
            [financial_market.add-total-cost-to-map.core :refer [add-total-cost-to-map]]))

(deftest add-total-cost-to-map-test
  (testing "Adding total cost to transactions"
    (let [transactions [{:operation "buy" :unit-cost 10.00 :quantity 100}
                        {:operation "sell" :unit-cost 15.00 :quantity 50}
                        {:operation "sell" :unit-cost 15.00 :quantity 50}]
          costs [1000.0 750.0 750.0]
          expected [{:operation "buy" :unit-cost 10.00 :quantity 100 :total-cost 1000.00}
                    {:operation "sell" :unit-cost 15.00 :quantity 50 :total-cost 750.00}
                    {:operation "sell" :unit-cost 15.00 :quantity 50 :total-cost 750.00}]
          results (map add-total-cost-to-map transactions costs)]
      (is (= expected results)))))