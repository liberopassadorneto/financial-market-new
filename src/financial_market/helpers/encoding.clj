(ns financial-market.helpers.encoding
  (:require
   [cheshire.core :as json]))

(defn clj->json
  [map-list]
  (json/generate-string map-list))
