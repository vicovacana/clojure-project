(ns core
  (:gen-class)
  (:require [maze :as maze]))

(defn -main
  "I don't do a whole lot ... yet."
  [_])
  (println (maze/make-grid 0 10))
