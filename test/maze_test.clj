(ns maze-test
  (:require [midje.sweet :refer :all]
            [maze :refer [make-grid make-grid-for-maze get-unvisited-neighbors remove-wall-unit remove-wall generate-maze]]))

(facts "Test maze init"
       (make-grid 3 3 3) => [[3 3 3] [3 3 3] [3 3 3]]
       (make-grid 2 1 {:a 5 :b 4}) => [[{:a 5 :b 4}] [{:a 5 :b 4}]]
       (make-grid 2 0 1) => [[] []]
       (make-grid 0 0 1) => []
       )

(facts "Test make-grid-for-maze"
  (count (make-grid-for-maze 2 3)) => 2
  (count (first (make-grid-for-maze 2 3))) => 3

  (get-in (make-grid-for-maze 1 1) [0 0 :visited?]) => false
  (get-in (make-grid-for-maze 1 1) [0 0 :walls :n]) => true
  (get-in (make-grid-for-maze 1 1) [0 0 :walls :e]) => true
  (get-in (make-grid-for-maze 1 1) [0 0 :walls :s]) => true
  (get-in (make-grid-for-maze 1 1) [0 0 :walls :w]) => true
)

(facts "Test get-unvisited-neighbors"

  (let [grid (make-grid-for-maze 3 3)]
    (set (get-unvisited-neighbors grid [1 1])))
  => #{[0 1] [1 2] [2 1] [1 0]}

  (let [grid (make-grid-for-maze 3 3)]
    (set (get-unvisited-neighbors grid [0 0])))
  => #{[0 1] [1 0]}

  (let [grid (assoc-in (make-grid-for-maze 3 3) [0 1 :visited?] true)]
    (set (get-unvisited-neighbors grid [0 0])))
  => #{[1 0]}
)

(facts "Test remove-wall-unit"

  (let [grid (make-grid-for-maze 1 1)
        new-grid (remove-wall-unit grid [0 0] :n)]
    (get-in new-grid [0 0 :walls :n]))
  => false

  (let [grid (make-grid-for-maze 1 1)
        new-grid (remove-wall-unit grid [0 0] :n)]
    (get-in new-grid [0 0 :walls :e]))
  => true
)

(facts "Test remove-wall"

    (let [grid (make-grid-for-maze 2 1)
        new-grid (remove-wall grid [0 0] [1 0])]
    
    (get-in new-grid [0 0 :walls :s]))
  => false

  (let [grid (make-grid-for-maze 2 1)
        new-grid (remove-wall grid [0 0] [1 0])]
    
    (get-in new-grid [1 0 :walls :n]))
  => false
       
 (let [grid (make-grid-for-maze 1 2)
        new-grid (remove-wall grid [0 0] [0 1])]
    
    (get-in new-grid [0 0 :walls :e]))
  => false

  (let [grid (make-grid-for-maze 1 2)
        new-grid (remove-wall grid [0 0] [0 1])]
    
    (get-in new-grid [0 1 :walls :w]))
  => false
)

(facts "Test generate-maze"
    (let [result (generate-maze 3 3)
        grid (:grid result)]
    
    (count grid) => 3
    (count (first grid)) => 3

    (every? true?
            (for [row grid
                  cell row]
              (:visited? cell))) => true

    (every? map? (:history result)) => true
      )
)

