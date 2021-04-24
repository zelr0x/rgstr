(ns rgstr.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [rgstr.events] ;; force compilation
            [rgstr.subs] ;; force compilation
            [rgstr.views :as v]))

(enable-console-print!)

(defn render []
  (rdom/render [v/ui]
               (js/document.getElementById "app")))

;; shadow-cljs hot reload.
(defn ^:dev/after-load clear-cache-and-render! []
  (rf/clear-subscription-cache!)
  (render))

(defn ^:export run []
  (rf/dispatch-sync [:initialize-db])
  (render))
