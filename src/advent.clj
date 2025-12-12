(ns advent
  (:require [clojure.java.io :as io]))

 ;(defn positiveQuote [a b]
 ;   (quot (Math/abs a) (Math/abs b)))

(defn addRight [pos steps]
  (let [new-pos (+ pos steps)
        res     (mod new-pos 100)
        crosses (quot new-pos 100)]
    [res crosses]))

(defn addLeft [pos steps]
  (let [new-pos (- pos steps)
        res     (mod new-pos 100)
        crosses (if (<= new-pos 0)
                  (quot (+ steps (dec pos)) 100)
                  0)]
    [res crosses]))

 (defn check [res currentZero zeros numberOfZeros]
    (if (and (= res 0) (> zeros 0)) [(inc currentZero) (+ numberOfZeros zeros) res]
                         (if (= res 0) [(inc currentZero) (inc numberOfZeros) res]
                              (if (> zeros 0) [currentZero (+ zeros numberOfZeros) res] [currentZero numberOfZeros res])))
 )

(defn lineReading [[currentZero numberOfZeros cur] line]
    (let [direction (first line) number (Integer/parseInt (apply str (rest line)))]
        (if (= direction \R)
            ( let [[res zeros] (addRight cur number)]
                (check res currentZero zeros numberOfZeros))
            ( let [[res zeros] (addLeft cur number)]
                (check res currentZero zeros numberOfZeros))
        )
    )
)

(defn findSantasPassword [name]
  (with-open [rdr (io/reader name)]
      (reduce lineReading [0 0 50] (line-seq rdr))
  )
)
