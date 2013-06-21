(ns technote.workorder
  (:require [hiccup.core :refer [html]]
            [technote.views.default :refer (default-page)]))

(defn new-workorder []
  (default-page
    [:form {:action "workorder-add"
            :method "post"
            :id     "new-workorder-form"}
      [:fieldset
        (map (fn [info]
                [:div
                  [:label {:for info} (clojure.string/capitalize info)]
                  [:input {:name info}]])
          ["company" "name" "street" "city" "zip" "phone"])
        [:div
          [:label {:for "problems"} "Problems - wonky css... :'("
            [:textarea#problems {:name "problems"
                                 :rows 8
                                 :cols :50
                                 :required ""}]]]
        [:div
          [:label {:for "submit"}]
          [:input#submit {:type "submit" :value "Get 'er dunn!!"}]]]]))

(defn workorder-add [stuff]
  (default-page
    stuff))

(defn workorder [id]
  (default-page
    [:div [:p "Got workorder with id: " id]]))
