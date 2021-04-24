(ns rgstr.table
  (:require [rgstr.util :refer [ensure-assoc]]))

(defn thead [opts]
  [:thead
   (:thead-opts opts)
   (for [[th-id col] (zipmap (range) (:cols opts))]
     ^{:key th-id}
     [:th
      (ensure-assoc (:th-opts col) :scope (fn [_ _] "col"))
      (:label col)])])

(defn tr [rec opts row-id]
  [:tr
   (:tr-opts opts)
   (for [[col-id {:keys [key tform]} :as cols] (zipmap (range) (:cols opts))]
     ^{:key (str row-id col-id)}
     [:td
      (:td-opts cols (:td-opts opts))
      ((or tform identity) (key rec))])])

(defn tbody [records opts]
  [:tbody
   (:tbody-opts opts)
   (for [[row-id rec] (zipmap (range) @records)]
     ^{:key row-id}
     [tr rec opts row-id])])

(defn table 
  "Creates a simple table. Vertical by default.
  opts must contain :cols mapped to column config.
  opts may contain options for each hiccup element 
  representing an html table tag in the form :tag-opts.
  opts :cols value may contain :th-opts and :td-opts
  for individual columns."
  [records opts]
  [:table
   (:table-opts opts)
   [thead opts]
   [tbody records opts]])
