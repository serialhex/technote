(ns technote.views.default
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html]]))

(defn default-page [& body]
  (html5 [:html
            [:head
              [:meta {:charset "utf-8"}]
              [:title "Technote"]
              [:link {:rel "stylesheet" :href "/css/normalize.css"}]
              [:link {:rel "stylesheet" :href "/css/flat-ui.css"}]
              ; [:link {:rel "stylesheet" :href "/css/styles.css"}]
              [:link {:rel "stylesheet" :href "/css/technote.css"}]
              ]
            [:body
              [:ul#sidebar
                [:li [:a {:href "/login"} "login"]]
                [:li [:a {:href "/workorder"} "workorder"]]
                [:li [:a {:href "/workorder/new"} "new workorder"]]]
              [:div#main
                (html body)]]
              [:script {}]]))

(defn four-oh-4 [x]
  (default-page
    [:div [:p "DAMMIT MAN!!! " [:b (:uri x)] " DOSN'T EXSIST!!!"]]))
