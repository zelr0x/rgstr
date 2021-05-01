(ns rgstr.util
  (:require [tick.alpha.api :as t]))

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

(defmulti to-date class)
(defmethod to-date java.util.Date [d] d)
(defmethod to-date java.time.LocalDate [ld] (ld->date ld))
