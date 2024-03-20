(ns financial_market.add-total-of-stocks-to-map.core-test
  (:require [clojure.test :refer :all]
            [financial_market.add-total-of-stocks-to-map.core :refer :all]))

(deftest test-add-total-of-stocks-to-map
  (testing "Function add-total-of-stocks-to-map"
    (let [transaction {:operation "buy", :unit-cost 10.00, :quantity 100}
          total-of-stocks 100
          expected {:operation "buy", :unit-cost 10.00, :quantity 100, :total-stocks 100}]
      (is (= expected (add-total-of-stocks-to-map transaction total-of-stocks))))))
