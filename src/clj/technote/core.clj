(ns technote.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :refer [html]]
            [technote.views.default :refer [default-page four-oh-4]]
            [technote.workorder :refer [new-workorder workorder workorder-add]]
            [technote.login :refer [login authenticate-user]]))



;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes

  ; to serve document root address
  (GET "/" [] (default-page [:div [:a {:href "/login"} "login"]]
                            [:div [:a {:href "/workorder"} "workorder"]]))

  ; login related pages
  (GET  "/login" [] (login))
  (POST "/login" [user password] (authenticate-user user password))

  ; workorder related pages
  (GET "/workorder" [] (new-workorder))
  (GET ["/workorder/:id" :id #"\d+"] [id] (workorder id))
  (POST "/workorder-add" [& stuff] (workorder-add stuff))

  ; to serve static pages saved in resources/public directory
  (route/resources "/")

  (route/not-found four-oh-4))

(def handler
  (handler/site app-routes))
