(ns technote.views.default
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html]]))

(defn default-page [& body]
  (html5 [:html
            [:head
              [:meta {:charset "utf-8"}]
              [:title "Technote"]
              [:link {:rel "stylesheet" :href "/css/flat-ui.css"}]
              [:link {:rel "stylesheet" :href "/css/styles.css"}]
              ]
            [:body
              (html body)]]))

(defn four-oh-4 [x]
  (default-page
    [:div [:p "DAMMIT MAN!!! " [:b (:uri x)] " DOSN'T EXSIST!!!"]]))
