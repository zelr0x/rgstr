(ns rgstr.handler
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :as ring-json]
            [ring.util.http-response :as rr]
            [rgstr.middleware :as m]
            [rgstr.util :as u]
            [rgstr.store :as store]
            [rgstr.app :as app]))

;; Only accepts dates as ISO 8601 strings.
;; todo: refactor deserialization and error handling.
(defroutes routes
  (route/resources "/")
  ;; task requirement
  (GET "/testapp" []
    (rr/content-type
     (rr/resource-response "index.html" {:root "public"})
     "text/html"))
  (context "/api/applications" []
    (GET "/" []
      (rr/ok (app/get-apps (store/db))))
    (POST "/" [title description applicant assignee due-date]
      (let [parse-res (u/try-isodate->ld due-date)
            due-date (:result parse-res)]
        (if (nil? due-date)
          (rr/bad-request "Could not parse date")
          (let [app {:app/title title
                     :app/description description
                     :app/applicant applicant
                     :app/assignee assignee
                     :app/due-date due-date}
                eid (app/upsert! store/conn app)]
            (if (some? eid) (rr/ok [eid])))))))
  (route/not-found "Not found"))

;; todo: handle undefined in requests?
(def app
  (-> routes
   (ring-json/wrap-json-body {:keys? true})
   (ring-json/wrap-json-response)
   (m/strip-nonroot-trailing-slash-handler)
   ;; no tls and no auth so I didn't bother with csrf either. TODO: switch to site-defaults
   (wrap-defaults api-defaults)))
