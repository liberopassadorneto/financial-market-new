(ns financial-market.inputs.digest
  (:require
   [clojure.java.io :as io]
   [financial-market.inputs.json :refer [json->clj]]))


(defn digest-json-file
  [path]
  (-> path
   (slurp)
   (json->clj)))

(defn digest-piped
  [data]
  (json->clj data))

(defn digest-data
  [data]
  (if (.exists (io/file data))
    (digest-json-file data)
    (digest-piped data)))

