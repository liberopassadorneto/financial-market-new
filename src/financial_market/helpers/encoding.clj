(ns financial-market.helpers.encoding
  (:require
   [cheshire.core :as cheshire]
   [clojure.pprint :as pprint]
   [clojure.data.json :as json]))

(defn clj->json
  [map-list]
  (let [json (json/write-str map-list)
        out-str (json/read-str json)]
    (pprint/pprint out-str)
    json))
