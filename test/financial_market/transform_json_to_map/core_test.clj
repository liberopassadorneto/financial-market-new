(ns financial_market.transform-json-to-map.core-test
  (:require [clojure.test :refer :all]
            [financial_market.transform-json-to-map.core :refer [parse-input-json]]))

(deftest parse-input-json-test
  (testing "Parsing JSON input"
    (let [json-input "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50}]"
          expected-output [{:operation "buy" :unit-cost 10.00 :quantity 100}
                           {:operation "sell" :unit-cost 15.00 :quantity 50}]
          actual-output (parse-input-json json-input)]
      (is (= expected-output actual-output)))))
