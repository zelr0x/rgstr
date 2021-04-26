(ns rgstr.util
  (:require [tick.alpha.api :as t]))

(defn try-isodate->ld [cs]
  "Parses the given date CharSequence using ISO 8601."
  (try
    (let [parsed (t/parse cs)]
      {:result parsed})
    (catch java.lang.IllegalArgumentException e
      {:error e})))

(defn ld->ldt [ld]
  "Transforms the given LocalDate to LocalDateTime
   using the time of midnight."
  (when-some [x ld] (t/at x (t/midnight))))

(defn ldt->date [ldt]
  "Parses the given LocalDateTime as Date."
  (when-some [x ldt] (t/inst ldt)))

(defn ld->date [ld]
  "Parses the given LocalDate as Date
  using the time of midnight."
  (when-some [x ld]
    (-> ld
        (ld->ldt)
        (ldt->date))))

;; FIXME: this is kinda major bad thing...
;; I'm not sure how and where use and destructure namespaced (using /) 
;; keywords properly. All solutions I tried don't seem to work, so I used this.
;; https://stackoverflow.com/a/44220595
(defn- transform-keywords [m]
  (into {}
        (map (fn [[k v]]
               (let [k (if (keyword? k) (keyword (name k)) k)
                     v (if (keyword? v) (name v) v)]
                 [k v]))
             m)))
(defn strip-ns [m]
  (clojure.walk/postwalk
   (fn [x]
     (if (map? x)
       (transform-keywords x)
       x))
   m))
