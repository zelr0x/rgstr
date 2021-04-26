(ns rgstr.store
  (:require [clojure.java.io :as io]
            [datomic.api :as d]
            [rgstr.util :as u])
  (:import datomic.Util))

(defn- schema [] (io/resource "schema.edn"))

(defn- read-txs [tx-resource]
  (with-open [tf (io/reader tx-resource)]
    (Util/readAll tf)))

(defn- transact-all
  ([conn txs]
   (transact-all conn txs nil))
  ([conn txs res]
   (if (seq txs)
     (transact-all conn (rest txs) @(d/transact conn (first txs)))
     res)))

;; todo: inject
(def ^:private dev-db-uri "datomic:mem://rgstr")
(def conn nil)

(defn db [] (d/db conn))

(defn initialize-db []
  (d/create-database dev-db-uri)
  (let [conn (d/connect dev-db-uri)]
    (transact-all conn (read-txs (schema)))
    conn))

(defn start []
  (alter-var-root #'conn (fn [_] (initialize-db))))

(defn stop []
  (alter-var-root #'conn (fn [c] (when c (d/release c)))))
