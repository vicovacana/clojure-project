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

(defn update-value [grid row col newValue]
  (assoc-in grid [row col] newValue))

(defn get-unvisited-neighbors [grid [x y]]
  (let [rows (count grid)
        cols(count (first grid))
        potential-neighbors [[(dec x) y] 
                             [x (inc y)]
                             [(inc x) y]
                             [x (dec y)]]]
        (filter (fn [[nx ny]] 
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
                  (remove-wall-unit [x1 y1] :s)
                  (remove-wall-unit [x2 y2] :n)
                  )
        (= xAxis -1) (-> grid
                  (remove-wall-unit [x1 y1] :n)
                  (remove-wall-unit [x2 y2] :s)
                  )
        (= yAxis 1) (-> grid
                  (remove-wall-unit [x1 y1] :e)
                  (remove-wall-unit [x2 y2] :w)
                  )
        (= yAxis -1) (-> grid
                  (remove-wall-unit [x1 y1] :w)
                  (remove-wall-unit [x2 y2] :e)
                  )
        )
  )
)

(defn add-shortcuts [grid rows cols num-shortcuts]
  (loop [g grid
         n num-shortcuts
         attempts 200]
    (if (or (<= n 0) (<= attempts 0))
      g
      (let [r (rand-int rows)
            c (rand-int cols)
            cell [r c]
            walls (get-in g [r c :walls])
            
            existing-walls (cond-> []
                             (and (:n walls) (> r 0)) (conj :n)
                             (and (:s walls) (< r (dec rows))) (conj :s)
                             (and (:e walls) (< c (dec cols))) (conj :e)
                             (and (:w walls) (> c 0)) (conj :w))]
        
        (if (empty? existing-walls)
          (recur g n (dec attempts))
          
          (let [dir (rand-nth existing-walls)
                neighbor (case dir
                           :n [(dec r) c]
                           :s [(inc r) c]
                           :e [r (inc c)]
                           :w [r (dec c)])]
            (recur (remove-wall g cell neighbor) (dec n) attempts)))))))
            
(defn generate-maze [rows cols]
  (let [initial-grid (make-grid-for-maze rows cols)
        start-col (rand-int cols)
        end-col (rand-int cols)
        start-cell [(dec rows) start-col]
        end-cell [0 end-col]

        grid-with-doors (-> initial-grid
                            (remove-wall-unit start-cell :s)
                            (remove-wall-unit end-cell :n))]

    (loop [grid (assoc-in grid-with-doors (conj start-cell :visited?) true)
           stack [start-cell]
           history []]

      (if (empty? stack)
        (let [num-shortcuts (int (* rows cols 0.15)) 
              final-grid (add-shortcuts grid rows cols num-shortcuts)]
          
          {:grid final-grid
           :start-cell start-cell
           :end-cell end-cell
           :history history})

        (let [current-cell (peek stack)
              neighbors (get-unvisited-neighbors grid current-cell)]

          (if (empty? neighbors)
            (let [new-stack (pop stack)]
              (if (seq new-stack) 
                (recur grid new-stack (conj history {:from current-cell :to (peek new-stack)}))
                (recur grid new-stack history)))

            (let [next-cell (rand-nth neighbors)
                  new-grid (-> grid (remove-wall current-cell next-cell)
                               (assoc-in (conj next-cell :visited?) true))
                  move {:from current-cell :to next-cell}]

              (recur new-grid (conj stack next-cell) (conj history move)))))))))

