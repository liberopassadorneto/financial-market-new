(ns financial-market.inputs.json
  (:require [cheshire.core :as json]))

(defn json->clj [json-input]
  (json/parse-string json-input true))

;; Example usage:
(comment
 (let [json-input "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50}]"
        parsed-data (json->clj json-input)]
   (println parsed-data)))
