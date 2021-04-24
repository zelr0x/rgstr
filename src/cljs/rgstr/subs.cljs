(ns rgstr.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :apps-data
  (fn [db _]
    (get-in db [:apps :data])))
