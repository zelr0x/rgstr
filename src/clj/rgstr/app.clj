(ns rgstr.app
  (:require [datomic.api :as d]
            [rgstr.util :as u]))

;; Without first returns a structure like [[{e1 map}, {e2 map}, ...]].
;; todo: find a way to avoid nested vector and flatten, if exists.
(defn get-apps [db]
  (-> (d/q '[:find (pull ?e [[:app/id :as :id]
                             [:app/title :as :title]
                             [:app/applicant :as :applicant]
                             [:app/assignee :as :assignee]
                             [:app/due-date :as :due-date]
                             [:app/description :as :description]])
             :where [?e :app/id]]
           db)
      flatten))

;; fixme: insert
(defn upsert! [conn app]
  (when (every? some? (vals app)) ;; todo: refactor check
    (let [tmp-id (d/tempid :db.part/user)
          gid (d/squuid)
          due-date (u/ld->date (:app/due-date app))
          app (-> app (assoc :db/id tmp-id
                             :app/id gid
                             :app/due-date due-date))
          tx @(d/transact conn [app])]
      (d/resolve-tempid (d/db conn) (:tempids tx) tmp-id))))
