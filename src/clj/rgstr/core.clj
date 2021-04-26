(ns rgstr.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [rgstr.handler :refer [app]]
            [rgstr.store :as store])
  (:gen-class))

(defn- start-server [port]
  (println "Starting server on port" port)
  (run-jetty #'app {:port port
                    :join? false}))

;; can't have params if used as lein-ring :init
(defn init []
  (store/start))

(defn -main [& [port]]
  (init)
  ;; Port number is a task requirement
  (start-server (Integer. (or port 8080))))
