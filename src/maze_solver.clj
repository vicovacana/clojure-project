(ns maze-solver (:require [maze :as m]))

;;heuristika - h(n)
(defn manhattan-distance [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) 
     (Math/abs (- y1 y2))))

(defn get-accessible-neighbors [grid [x y]]
  (let [walls (get-in grid [x y :walls])
        rows (count grid)
        cols (count (first grid))]
    (cond-> []
          (and (not (:n walls)) (>= (dec x) 0)) (conj [(dec x) y])
          (and (not (:s walls)) (< (inc x) rows)) (conj [(inc x) y])
          (and (not (:e walls)) (< (inc y) cols)) (conj [x (inc y)])
          (and (not (:w walls)) (>= (dec y) 0)) (conj [x (dec y)]))))

(defn evaluate-neighbor [current-node end-node state neighbor]
  (let [new-neighbor-g (+ (get (:g-score state) current-node) 1)
        old-neighbor-g (get (:g-score state) neighbor 99999)]
    (if (< new-neighbor-g old-neighbor-g)
      ;;new state
      {:open-set (conj (:open-set state) neighbor)
       :came-from (assoc (:came-from state) neighbor current-node)
       :g-score (assoc (:g-score state) neighbor new-neighbor-g)
       :f-score (assoc (:f-score state) neighbor (+ new-neighbor-g (manhattan-distance neighbor end-node)))
       :history (:history state)}
      ;;else
      state)))

(defn make-path [came-from current-node]
  (loop [path (list current-node)
         node current-node]
   (let [prev-node (get came-from node)]
     (if prev-node
       (recur (cons prev-node path) prev-node)
       path))))

(defn solve-a-star [grid start-node end-node]
  (loop [state {:open-set #{start-node}
                :came-from {}
                :g-score {start-node 0}
                :f-score {start-node (manhattan-distance start-node end-node)}
                :history []}]

    (let [open-set (:open-set state)]
      (if (empty? open-set)
      {:path [] :history (:history state)}
      
      (let [current (apply min-key #(get (:f-score state) % 999999) open-set)
            new-state {:open-set (disj open-set current)
                       :came-from (:came-from state)
                       :g-score (:g-score state)
                       :f-score (:f-score state)
                       :history (conj (:history state) current)}]
        
        (if (= current end-node) 
          {:path (make-path (:came-from state) current)
           :history (:history new-state)
        }
          
          (let [neighbors (get-accessible-neighbors grid current)
                state-after-neighbors (reduce (fn [curr-state neigh] 
                                                (evaluate-neighbor current end-node curr-state neigh))
                                              new-state
                                              neighbors)]
            
            (recur state-after-neighbors))))))))

(defn a-star-test [] 
  (let [maze-data (m/generate-maze 10 10) 
        grid (:grid maze-data)
        start (:start-cell maze-data)
        end (:end-cell maze-data)] 
    
    (println "TRAZENJE PUTA")
    (println "=========================================")
    
    (let [solution (solve-a-star grid start end)
          path (:path solution)]
      
      (if (empty? path)
        (println "ERROR")
        (do
          (println "SUCCESS")
          (println "Putanja koordinate:" path))))))

;;---------------------------------------------------

;;ALGORITMI

;;A-STAR (A*)
;;Koristi heuristiku, pamti koliko je otp cilj udaljen, kao i koliko je do sada prosao i koliko mu je ostalo do cilja
;;f(n)=g(n)+h(n)
;;g(n) - G-score => cena puta (u lavirintu cena svakog polja je 1) = br koraka
;;h(n)- H-score (heuristika) => pogadjanje koliko do cilja
;;f(n) - ukupna cena polja - zelimo da minimizujemo