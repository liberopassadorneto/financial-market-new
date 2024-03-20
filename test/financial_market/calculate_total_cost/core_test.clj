(ns financial_market.calculate-total-cost.core-test
  (:require [clojure.test :refer :all]
            [financial_market.calculate-total-cost.core :refer [calculate-total-cost]]))

(deftest test-calculate-total-cost
  (testing "Should calculate total cost for a sequence of operations"
    (let [operations [{:operation "buy" :unit-cost 10.00 :quantity 100}
                      {:operation "sell" :unit-cost 15.00 :quantity 50}
                      {:operation "sell" :unit-cost 15.00 :quantity 50}]
          result (calculate-total-cost operations)
          expected [1000.0 750.0 750.0]]
      (is (= expected result)))))
