(ns rgstr.handler
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
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
      (store/put-app! store/app-store {:title title
                                       :description description
                                       :applicant applicant
                                       :assignee assignee
                                       :due-date due-date})))
  (route/not-found "Not found"))

(def app
  (-> routes
   (ring-json/wrap-json-body {:keys? true})
   (ring-json/wrap-json-response)
   (m/strip-nonroot-trailing-slash-handler)
   ;; no tls and no auth so I didn't bother with csrf either. TODO: switch to site-defaults
   (wrap-defaults api-defaults)))
