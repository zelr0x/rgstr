(ns rgstr.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [rgstr.handler :refer [app]]
            [rgstr.store :as store])
  (:gen-class))

(defn- start-server [port]
  (run-jetty #'app {:port port
                    :join? false}))

;; can't have params if used as lein-ring :init
(defn init []
  (store/start))

(defn -main [& [port]]
  ;; Port number is a task requirement
  (init)
  (start-server (Integer. (or port 8080))))
