(ns rgstr.db
  (:require [re-frame.core :as rf]))

(def default-db
  {:apps {:data []
          :loading? false}
   :app-create-form {:data {}}})
