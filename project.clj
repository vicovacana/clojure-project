(defproject clojure-project "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.12.2"]]

  :profiles
  {:dev {:dependencies [[midje "1.10.10"]]}
   :uberjar {:aot :all}})
