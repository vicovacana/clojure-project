(ns maze-test
  (:require [midje.sweet :refer :all]
            [maze :refer [make-grid getIndex]]))

(facts "Test maze init"
       (make-grid 3 3 3) => [[3 3 3] [3 3 3] [3 3 3]]
       (make-grid 2 1 {:a 5 :b 4}) => [[{:a 5 :b 4}] [{:a 5 :b 4}]]
       (make-grid 2 0 1) => [[] []]
       (make-grid 0 0 1) => []
       )

(facts "Test getIndex function"
       (getIndex 0 0 3 3) => 0
       (getIndex 0 2 3 3) => 2
       (getIndex 2 0 3 3) => 6
       (getIndex 2 2 3 3) => 8
       (getIndex 1 1 3 3) => 4
       (getIndex -1 1 3 3) => nil
       (getIndex 3 1 3 3) => nil
       (getIndex 1 -1 3 3) => nil
       (getIndex 1 3 3 3) => nil)
