(ns state-server.core
  (:gen-class)
  (use [ring.adapter.jetty :only (run-jetty)])
  (use [ring.middleware.params :only (wrap-params)])
  (use [vistar-app.logic])
  (require [clojure.string :as str]))

(defn get-coords
  "This will take the query string and build a hash-map from it."
  [query-string]
  (->> (str/split query-string #"&")
       (map #(str/split % #"="))
       (map (fn [[k v]] [(keyword k) v]))
       (into {})))

(defn app*
  [{:keys [query-string]}]
  {:body (find-state (get-coords query-string))})

(def app (wrap-params app*))

(def server (run-jetty #'app {:port 8080
                              :join false}))
