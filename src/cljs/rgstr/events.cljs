(ns rgstr.events
  (:require [re-frame.core :as rf]
            [rgstr.db :refer [default-db]]
            [ajax.core :refer [GET]]
            [rgstr.api :as api]))

;; todo: add form clearing when... resp-ok? mostly a ux question
(rf/reg-event-db
  :app-create-form-submit
  (fn [db [_ data]]
    (api/create-app! @data)
    (rf/dispatch [:apps-request-data])
    db))

(rf/reg-event-db
  :apps-resp-ok
  (fn [db [_ resp]]
    (-> db
        (assoc-in [:apps :data] resp)
        (assoc-in [:apps :loading?] false))))

(rf/reg-event-db
  :apps-resp-err
  (fn [db _]
    (js/alert "Failed to load apps") ;; FIXME
    (assoc-in db [:apps :loading?] false)))

(rf/reg-event-db
  :apps-request-data
  (fn [db _]
    (api/get-apps
     {:handler #(rf/dispatch [:apps-resp-ok %1])
      :error-handler #(rf/dispatch [:apps-resp-err %1])})
    (assoc-in db [:apps :loading?] true)))

(rf/reg-event-db
  :initialize-db
  (fn [db _]
    (rf/dispatch [:apps-request-data])
    (merge db default-db)))
