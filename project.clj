(defproject financial-market "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.5.0"]
                 [cheshire "5.12.0"]
                 [com.hyperfiddle/rcf "20220926-202227"]]
  :main ^:skip-aot financial-market.core
  :profiles {:uberjar {:aot :all}})
  ;; :repl-options {:init-ns financial_market.main})

  ;; :target-path "target/%s"
  ;; :profiles {:uberjar {:aot :all}})
