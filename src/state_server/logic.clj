(ns state-server.logic
  (:gen-class)
  (require [cheshire.core :as cat]))


;; Slurps the JSON to create a Clojure hash-map of states
(def states (cat/parse-string (slurp "resources/states.json") true))

(defn in?
  "contains? has some weird behavior. Returns true if the coll contains the elm."
  [coll elm]
  (some #(= elm %) coll))

;; Checks if not null, used for filtering
(def not-nil?
  (complement nil?))

(defn parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*$" s)
    (read-string s)))

(defn coords->point
  "Converts a hash-map of coords into a vector."
  [coords]
  (let [{:keys [latitude longitude]} coords]
    [(parse-number longitude) (parse-number latitude)]))

(defn colinear?
  "Check to see if a point, p, is colinear to points x and y"
  [p x y]
  (or (and (<= x p) (> y p))
      (and (> x p) (<= y p))))

(defn crosses?
  "Checks to see if a ray from point p will intersect an edge from point (x1,y1) to point (x2,y2)."
  [[px py] [[x1 y1] [x2 y2]]]
  (let [vt (/ (- py y1) (- y2 y1))]
    (< px (+ x1 (* vt (- x2 x1))))))

(defn num-crosses
  "Returns 1 for an intersection with a ray from point p to the edge and 0 for a lack thereof"
  [point edge]
  (let [[_ py] point
        [[_ y1] [_ y2]] edge]
    (if (colinear? py y1 y2)
      (if (crosses? point edge)
        1
        0)
      0)))

(defn count-crosses
  "Counts the number of times a ray from point p will intersect the edges of a polygon."
  [point polygon]
  (reduce + (for [n (range (dec (count polygon)))]
              (num-crosses point [(nth polygon n)
                                  (nth polygon (inc n))]))))

(defn inside-polygon?
  "Determines whether or not a point is in a polygon."
  [point polygon]
  (odd? (count-crosses point polygon)))

(defn inside-state?
  "Given a point [px py] and a state {:state 'State name', :border [[e1x e1y] [e2x e2y] ... [eNx eNy]]}, this will tell you if that point is in that state."
  [point state]
  (let [border (:border state)]
    (if (or (in? border point)
            (inside-polygon? point (:border state)))
      (:state state))))

(defn find-state
  "Given a set of coords as a hash-map, this will tell you what state those coords are in."
  [coords]
  (let [point (coords->point coords)
        state (filter not-nil? (map (partial inside-state? point) states))]
    state))