(ns rgstr.handler
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :as ring-json]
            [ring.util.http-response :as rr]
            [rgstr.store :as store]))

(defroutes routes
  ;; Context name is a task requirement
  (context "/testapp" []
    (GET "/" [] "TODO root")
    (context "/applications" []
      (GET "/" []
        (rr/ok (store/get-apps store/app-store)))
      (POST "/" [title description applicant 
                 assignee due-date]
        "TODO post")))
  (route/not-found "Not found"))

(def app
  (-> routes
   (ring-json/wrap-json-body {:keys? true})
   (ring-json/wrap-json-response)
   (wrap-defaults site-defaults)))
