(ns state-server.server
  (:gen-class)
  (use [ring.adapter.jetty :only (run-jetty)])
  (use [ring.middleware.params :only (wrap-params)])
  (use [state-server.logic]))

;; Server setup

(defn app* [{:keys [query-string]}]
  {:body (find-state (get-coords query-string))})

(def app (wrap-params app*))


;; Start's the server with `lein run` or
;; from running an uberjar

(defn -main [& args]
  (run-jetty #'app {:port 8080
                    :join false}))

;(def server (run-jetty #'app {:port 8080
;                              :join false}))