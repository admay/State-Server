(ns state-server.core-test
  (:require [expectations :refer :all]
            [state-server.core :refer :all]
            [state-server.logic :refer :all]))

;; in
(expect true
        (in? [1 2 3] 1))

(expect #{true}
        (into #{}
              (map #(in? [1 "a" \b {}] %)
                   [1 "a" \b {}])))

;; (not-nil? nil) should return nil
(expect false
        (not-nil? nil))

;; None of these should be nil including empty sequences
(expect 0
        (count
          (filter #(= false %)
                  (map not-nil? [{} () [] "A" 1 \a]))))

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