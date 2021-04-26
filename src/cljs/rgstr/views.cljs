(ns rgstr.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [rgstr.table :refer [table]]))

(defn hero []
  [:div.hero.is-primary 
   [:div.hero-body.ml-5
    [:h1.title "rgstr"]
    [:h2.subtitle "rgstr applications"]]])

(defn app-create-form [title applicant assignee due-date description]
  (let [s (r/atom {:title title
                   :applicant applicant
                   :assignee assignee
                   :due-date due-date
                   :description description})]
    (fn []
      [:form.form {:on-submit (fn [e]
                                (.preventDefault e)
                                (rf/dispatch [:app-create-form-submit s]))}
       [:div.field
        [:label.label "Title"]
        [:div.control
         [:input.input {:type :text :name :title
                        :value (:title @s)
                        :on-change #(swap! s assoc :title (-> % .-target .-value))}]]]
       [:div.field
        [:label.label "Applicant"]
        [:div.control
         [:input.input {:type :text :name :applicant
                        :value (:applicant @s)
                        :on-change #(swap! s assoc :applicant (-> % .-target .-value))}]]]
       [:div.field
        [:label.label "Assignee"]
        [:div.control
         [:input.input {:type :text :name :assignee
                        :value (:assignee @s)
                        :on-change #(swap! s assoc :assignee (-> % .-target .-value))}]]]
       [:div.field
        [:label.label "Due date"]
        [:div.control
         [:input.input {:type :text :name :due-date
                        :value (:due-date @s)
                        :on-change #(swap! s assoc :due-date (-> % .-target .-value))}]]]
       [:div.field
        [:label.label "Description"]
        [:div.control
         [:textarea.textarea {:name :description
                              :on-change #(swap! s assoc :description (-> % .-target .-value))}
          (:description @s)]]]
       [:div.field.is-grouped.is-grouped-right
        [:p.control.mt-3
         [:input.button.is-primary {:value "Register" :type "submit"}]]]])))

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
