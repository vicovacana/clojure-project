(ns maze)

(defn make-grid [rows cols initial]
  (vec (repeat rows
               (vec (repeat cols initial)))))

;(def grid (make-grid 3 4 0))
;(get-in grid [row col])

;(assoc-in grid [1 2] 5) //postavlja novu vrednost polja i tako se dobija novi grid jer su strukture imutabilne

(defn updateValue [grid row col newValue]
  (assoc-in grid [row col] newValue))

(defn getIndex [rowIndex colIndex rows cols]
  (when (and (>= rowIndex 0)
             (< rowIndex rows)
             (>= colIndex 0)
             (< colIndex cols))
    (+ colIndex (* rowIndex cols))))

(defn getNeighbors [rowIndex colIndex grid]
  (let [rows (count grid)
        cols (count (first grid))]
    {
     ;mogu biti nil
     :top (getIndex (- rowIndex 1) colIndex cols rows)
     :right (getIndex rowIndex (+ colIndex 1) cols rows)
     :bottom (getIndex (+ rowIndex 1) colIndex cols rows)
     :left (getIndex rowIndex (- colIndex 1) cols rows)
     }
    ))