(ns rgstr.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :apps-data
  (fn [db _]
    (get-in db [:apps :data])))

(rf/reg-sub
  :app-create-form-data
  (fn [db _]
    (get-in db [:app-create-form :data])))

(rf/reg-sub
  :app-create-form-value
  :<- [:app-create-form-data]
  (fn [doc [_ path]]
    (get-in doc path)))


