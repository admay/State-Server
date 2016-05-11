(ns state-server.core
  (:gen-class)
  (use [ring.adapter.jetty :only (run-jetty)])
  (use [ring.middleware.params :only (wrap-params)])
  (use [state-server.logic]))

(defn app*
  [{:keys [query-string]}]
  {:body (find-state (get-coords query-string))})

(def app (wrap-params app*))

;(def server (run-jetty #'app {:port 8080
;                              :join false}))