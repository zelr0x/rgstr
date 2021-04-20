(ns rgstr.store
  (:import (java.util UUID)))

(defn- identify [m]
  (assoc m :id (UUID/randomUUID)))

(defrecord App
    [id title description applicant assignee due-date])

(defrecord AtomStore [data])

(defprotocol AppStore
  (get-apps [store])
  (put-app! [store app]))

(extend-protocol AppStore
  AtomStore
  (get-apps [store] (get @(:data store) :apps))
  (put-app! [store app]
    (swap! (:data store)
           update-in [:apps] conj (identify app))))

(def app-store
  (->AtomStore (atom {:apps []})))
