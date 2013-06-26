(ns technote.views.workorder
  (:require [hiccup.core :refer [html]]
            [technote.views.default :refer [default-page]]
            [technote.database :refer [insert-stuff get-stuff]]))

(defn new-workorder []
  (default-page
    [:form {:action "workorder-add"
            :method "post"
            :id     "new-workorder-form"}
      [:fieldset
        (map (fn [[info argz]]
                [:div
                  [:label {:for info} (clojure.string/capitalize info)]
                  [:input (merge {:type "text" :name info} argz)]])
          { "company" {}
            "name"    {:required ""}
            "street"  {:required ""}
            "city"    {:required ""}
            "zip"     {:required ""}
            "phone"   {:required ""}})
        [:div
          [:label {:for "problems"} "Problems - wonky css... :'("
            [:textarea#problems {:name "problems"
                                 :rows 8
                                 :cols 50
                                 :required ""}]]]
        [:div
          [:label {:for "submit"}]
          [:input#submit {:type "submit" :value "Get 'er dunn!!"}]]]]))

(defn workorder-add [stuff]
  (default-page
    (let [data (insert-stuff stuff)]
      [:div
        (str "Thank you " (:name data) ".  "
             "Your workorder number is: " (:_id data) ".  "
             "We will contact you once we know anything.")])))

(defn workorder [id]
  (default-page
    [:div [:p "Got workorder with id: " id]]))

(defn list-workorders []
  (default-page
    [:div
      (get-stuff)]))
