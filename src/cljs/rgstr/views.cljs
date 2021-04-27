(ns rgstr.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [rgstr.table :refer [table]]
            [reagent-forms.core :refer [bind-fields]]
            [clojure.string :as str]))

(defn hero []
  [:div.hero.is-primary 
   [:div.hero-body.ml-5
    [:h1.title "rgstr"]
    [:h2.subtitle "rgstr applications"]]])

(def events
  {:get (fn [path] @(rf/subscribe [:app-create-form-value path]))
   :save! (fn [path value] (rf/dispatch [:app-create-form-set-value path value]))
   :update! (fn [path save-fn value]
              ; save-fn should accept two arguments: old-value, new-value
              (rf/dispatch [:app-create-form-update-value save-fn path value]))
   :doc (fn [] @(rf/subscribe [:app-create-form-data]))})

(defn app-create-form []
  [bind-fields
   [:form.form {:on-submit (fn [e]
                             (.preventDefault e)
                             (rf/dispatch [:app-create-form-submit]))}
    [:div.field
     [:label.label "Title"]
     [:div.control
      [:input.input {:field :text :id :title}]]]
    [:div.field
     [:label.label "Applicant"]
     [:div.control
      [:input.input {:field :text :id :applicant}]]]
    [:div.field
     [:label.label "Assignee"]
     [:div.control
      [:input.input {:field :text :id :assignee}]]]
    [:div.field
     [:label.label "Due date"]
     [:div.control
      [:input.input.datepicker {:field :datepicker :id :due-date
                                :auto-close? true
                                :date-format "yyyy-mm-dd" :inline true}]]]
    [:div.field
     [:label.label "Description"]
     [:div.control
      [:textarea.textarea {:field :textarea :id :description}]]]
    [:div.field.is-grouped.is-grouped-right
     [:p.control.mt-3
      [:input.button.is-primary {:value "Register" :type "submit"}]]]]
   events])

(defn app-create-section []
  [:div.section  
   [:div.columns.is-desktop.is-centered.mb-2
    [:div.column.is-half
     [app-create-form]]]])

(defn apps []
  [table
   (rf/subscribe [:apps-data])
   {:table-opts {:class ["table" "is-striped" "center"]}
    :thead-opts {:class "thead"}
    :th-opts {:class "th"}
    :tbody-opts {:class "tbody"}
    :tr-opts {:class "tr"}
    :td-opts {:class "td"}
    :cols [{:key :id, :label "ID"}
           {:key :title, :label "Title"}
           {:key :description, :label "Description",
            :tform #(if (> (count %) 40) (str (subs % 0 37) "...") %)} ;; showcase of tform; todo: impl clamping
           {:key :assignee, :label "Assignee"}
           {:key :due-date, :label "Due Date"}]}])

(defn ui []
  [:section
   [hero]
   [:div.section
    [:div.container.is-max-desktop
     [:h2.title "Applications"]
     [app-create-section]
     [apps]]]])
