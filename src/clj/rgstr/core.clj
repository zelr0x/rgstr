(ns rgstr.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [rgstr.handler :refer [app]])
  (:gen-class))

(defn- start-server [port]
  (run-jetty #'app {:port port
                    :join? false}))

(defn -main [& [port]]
  ;; Port number is a task requirement
  (start-server (Integer. (or port 8080))))
