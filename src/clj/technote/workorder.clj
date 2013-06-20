(ns technote.workorder
  (:require [hiccup.core :refer [html]]
            [technote.views.default :refer (default-page)]))

(defn new-workorder []
  (default-page
    [:form {:action "workorder-add"
            :method "post"
            :id     "new-workorder-form"}
      (map (fn [info]
              [:div
                [:label {:for info} (clojure.string/capitalize info)]
                [:input {:name info}]])
        ["company" "name" "street" "city" "zip" "phone"])]))

(defn workorder-add [stuff]
  (default-page
    stuff))

(defn workorder [id]
  (default-page
    [:div [:p "Got workorder with id: " id]]))
