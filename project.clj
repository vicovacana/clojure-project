(defproject clojure-project "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [ring/ring-core "1.15.3"]
                 [ring/ring-jetty-adapter "1.15.3"]
                 [ring/ring-json "0.5.1"]
                 [compojure "1.7.2"]]

  :profiles
  {:dev {:dependencies [[midje "1.10.10"]]}
   :uberjar {:aot :all}}
  
  :main server)
