(ns rgstr.events
  (:require [re-frame.core :as rf]
            [rgstr.db :refer [default-db]]
            [ajax.core :refer [GET]]
            [rgstr.api :as api]
            [tick.alpha.api :as t]))

;; todo: add form clearing when... resp-ok? mostly a ux question
(rf/reg-event-db
  :app-create-form-submit
  (fn [db [_ data]]
    (let [app (get-in db [:app-create-form :data]) ;; FIXME: path shouldn't be here I believe, prettify
          d (:due-date app)
          d (if (map? d) ;; move to views.cljs
              (js/Date.UTC (:year d) (dec (:month d)) (:day d))
              d)
          app (assoc app :due-date d)]
      (api/create-app! app))
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
  :app-create-form-set-value
  (fn [db [_ path value]]
    (assoc-in db (into [:app-create-form :data] path) value)))

(rf/reg-event-db
  :app-create-form-update-value
  (fn [db [_ f path value]]
    (update-in db (into [:app-create-form :data] path) f value)))

(rf/reg-event-db
  :initialize-db
  (fn [db _]
    (rf/dispatch [:apps-request-data])
    (merge db default-db)))
