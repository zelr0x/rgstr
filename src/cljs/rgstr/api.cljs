(ns rgstr.api
  (:require [ajax.core :refer [GET POST]]))

(defn with-response-format [opts]
  (let [keywords? (:keywords? opts true)
        response-format (if keywords? 
                          :json 
                          (:response-format opts :json))]
    (assoc opts
           :keywords? keywords?
           :response-format response-format)))

(defn get-apps [opts]
  (GET "/api/applications"
       (with-response-format opts)))

(defn create-app! [app]
  (POST "/api/applications" {:url-params app
                             :format :json}))
