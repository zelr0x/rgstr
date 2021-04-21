(ns rgstr.handler
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :as ring-json]
            [ring.util.http-response :as rr]
            [rgstr.store :as store]
            [rgstr.middleware :as m]))

(defroutes routes
  (route/resources "/")
  ;; task requirement
  (GET "/testapp" []
    (rr/content-type
     (rr/resource-response "index.html" {:root "public"})
     "text/html"))
  (context "/api/applications" []
    (GET "/" []
      (rr/ok (store/get-apps store/app-store)))
    (POST "/" [title description applicant assignee due-date]
      "TODO post"))
  (route/not-found "Not found"))

(def app
  (-> routes
   (ring-json/wrap-json-body {:keys? true})
   (ring-json/wrap-json-response)
   (m/strip-nonroot-trailing-slash-handler)
   (wrap-defaults site-defaults)))
