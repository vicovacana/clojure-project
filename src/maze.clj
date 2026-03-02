(ns maze)

(defn make-grid [rows cols initial]
  (vec (repeat rows
               (vec (repeat cols initial)))))

(defn make-grid-for-maze [rows cols]
  (make-grid rows cols {
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

(defn get-unvisited-neighbors [grid [x y]]
  (let [rows (count grid)
        cols(count (first grid))
        potential-neighbors [[(dec x) y] ;;svi cvorovi okolo
                             [x (inc y)]
                             [(inc x) y]
                             [x (dec y)]]]
        (filter (fn [[nx ny]] ;;proveravamo jel visited true
                 (and (>= nx 0) (< nx rows)
                      (>= ny 0) (< ny cols)
                      (not (get-in grid [nx ny :visited?]))))
               potential-neighbors))
)

(defn remove-wall-unit[grid [row col] direction]
    (assoc-in grid [row col :walls direction] false)  
)

(defn remove-wall [grid [x1 y1] [x2 y2]]
  (let [xAxis (- x2 x1)
        yAxis (- y2 y1)]
        (cond
        (= xAxis 1) (-> grid
                  (remove-wall-unit [x1 y1] :e)
                  (remove-wall-unit [x2 y2] :w)
                  )
        (= xAxis -1) (-> grid
                  (remove-wall-unit [x1 y1] :w)
                  (remove-wall-unit [x2 y2] :e)
                  )
        (= yAxis 1) (-> grid
                  (remove-wall-unit [x1 y1] :s)
                  (remove-wall-unit [x2 y2] :n)
                  )
        (= yAxis -1) (-> grid
                  (remove-wall-unit [x1 y1] :n)
                  (remove-wall-unit [x2 y2] :s)
                  )
        )
  )
)

(defn generate-maze [rows cols]
  (let [initial-grid (make-grid-for-maze rows cols)
        start-cell [0 0]] ;;todo napravi da start cell bude random celija iz prvog reda
    (loop [grid (assoc-in initial-grid [0 0 :visited?] true)
           stack [start-cell]]
      
      (if (empty? stack)
        grid

        (let [current-cell (peek stack) 
              neighbors (get-unvisited-neighbors grid current-cell)]
          
          (if (empty? neighbors)
            (recur grid (pop stack)) ;;vracaj za jedan

            (let [next-cell (rand-nth neighbors)
                  new-grid (-> grid (remove-wall current-cell next-cell)
                               (assoc-in (conj next-cell :visited?) true))]
              
              (recur new-grid (conj stack next-cell)) ;;dodala novi na stack
              )
           )
          )
        )
     )
    )
  )

;;todo dodaj testove za ove fje

;;----------------------------------------------------
;;NOTES
;;DFS Backtracking -> Depth-First Search
;;Uzimam jednog komsiju i pakujem ga u stack, zatim uzimam njegovog komsiju itd.
;;Kada stignem do cvora koji nema neposecene komsije skidam ga sa stack-a. Zatim za prethodnog komsiju na stack-u vrsim proveru
;;Provera i skidanje sa stack-a se radi dok se stack ne isprazni

;;prilikom posete skidam zidove -> zidovi ce biti borderi oko celije

;;thread-first macro (->) ubacivanje rezultat jedog izraza u drugi
;;peek uzima sa vrha stack-a ali ga ne brise
