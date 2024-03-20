(ns financial_market.calculate-total-of-stocks.core-test
  (:require [clojure.test :refer :all]
            [financial_market.calculate-total-of-stocks.core :refer [calculate-total-of-stocks]]))

(deftest test-calculate-total-of-stocks
  (testing "calculate-total-of-stocks function"
    (let [transactions [{:operation "buy" :unit-cost 10.00 :quantity 100}
                        {:operation "sell" :unit-cost 15.00 :quantity 50}
                        {:operation "sell" :unit-cost 15.00 :quantity 50}]
          expected [100 50 0]]
      (is (= (calculate-total-of-stocks transactions) expected)))))