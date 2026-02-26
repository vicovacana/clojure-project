(ns maze)

(defn make-grid [rows cols initial]
  (vec (repeat rows
               (vec (repeat cols initial)))))

(defn makeEmptyMaze[mazeSize]
  (make-grid mazeSize mazeSize {
      :visited? false
      :walls {
        :n true
        :e true
        :s true
        :w true
      }
  })
)

;(def grid (make-grid 3 4 0))
;(get-in grid [row col])

;(assoc-in grid [1 2] 5) //postavlja novu vrednost polja i tako se dobija novi grid jer su strukture imutabilne

(defn update-value [grid row col newValue]
  (assoc-in grid [row col] newValue))

(defn get-index [rowIndex colIndex rows cols]
  (when (and (>= rowIndex 0)
             (< rowIndex rows)
             (>= colIndex 0)
             (< colIndex cols))
    (+ colIndex (* rowIndex cols))))

(defn get-neighbors [rowIndex colIndex grid]
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

(defn remove-wall-unit[maze coords direction]
    (assoc-in maze [coords :walls direction] value)  
)

(defn remove-wall [grid [x1 y1] [x2 y2]]
  (let [xAxis (- x2 x1)
        yAxis (- y2 y1)]
        (cond
        (= xAxis 1) (-> maze
                  (remove-wall-unit [x1 y1] :e)
                  (remove-wall-unit [x2 y2] :w)
                  )
        (= xAxis -1) (-> maze
                  (remove-wall-unit [x1 y1] :w)
                  (remove-wall-unit [x2 y2] :e)
                  )
        (= yAxis 1) (-> maze
                  (remove-wall-unit [x1 y1] :s)
                  (remove-wall-unit [x2 y2] :n)
                  )
        (= yAxis -1) (-> maze
                  (remove-wall-unit [x1 y1] :n)
                  (remove-wall-unit [x2 y2] :s)
                  )
        )
  )
)
;; generisanje lavirinta todo sutra