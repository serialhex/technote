(ns technote.views.default
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.def  :refer [defhtml]]
            [hiccup.core :refer [html]]
            [hiccup.element :refer [unordered-list
                                    link-to]]))

(defhtml default-page [& body]
  [:html
    [:head
      [:meta {:charset "utf-8"}]
      [:title "Technote"]
      (include-css  "/css/normalize.css"
                    "/css/flat-ui.css"
                    ; "/css/styles.css"
                    "/css/technote.css")]
    [:body
      (unordered-list {:id "sidebar"}
        [(link-to "/login"          "Login")
         (link-to "/workorder"      "Workorders")
         (link-to "/workorder/new"  "New Workorder")
         (link-to "/customer"       "Customers")])
      [:div#main body]]
      [:script {}]])

(defn four-oh-4 [x]
  (default-page
    [:div [:p "DAMMIT MAN!!! " [:b (:uri x)] " DOSN'T EXSIST!!!"]]))
