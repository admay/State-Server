(ns state-server.core-test
  (:require [expectations :refer :all]
            [state-server.server :refer :all]
            [state-server.logic :refer :all]))

;;;;; in
(expect true
        (in? [1 2 3] 1))

(expect #{true}
        (into #{}
              (map #(in? [1 "a" \b {}] %)
                   [1 "a" \b {}])))

;;;;; nil
;; (not-nil? nil) should return nil
(expect false
        (not-nil? nil))

;; None of these should be nil including empty sequences
(expect 0
        (count
          (filter #(= false %)
                  (map not-nil? [{} () [] "A" 1 \a]))))

;;;;; parse-number
;; parse-number should return nil unless it is _only_ a number
(expect 0
        (count
          (filter #(not-nil? %)
                  (map parse-number
                       ["q" "q1" "1q" "1q1" "q1q" "1.q" "q.1"]))))

;; Integers and decimals should be good
(expect 1
        (parse-number "1"))

(expect 1.1
        (parse-number "1.1"))

;; Fractions will _not_ be parsed
(expect nil
        (parse-number "1/2"))

;; get-coords
(expect {:longitude "42.45"
         :latitude "32.23"}
        (get-coords "longitude=42.45&latitude=32.23"))

(expect {nil nil}
        (get-coords "=&="))

;;;;; coords->point
(expect [1 5]
        (coords->point {:longitude 1
                        :latitude 5}))

;; The name of the keys shouldn't matter
(expect [10 20]
        (coords->point {:a 10
                        :b 20}))

;;;;; colinear
(expect true
        (colinear? 5 0 10))

(expect true
        (colinear? 0 0 10))

(expect false
        (colinear? 0 0 0))

(expect false
        (colinear? 0 1 4))

(expect false
        (colinear? 10 1 5))

(expect false
        (colinear? 5 0 5))