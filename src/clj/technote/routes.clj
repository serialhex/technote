(ns technote.routes
  (:use compojure.core
        [ring.adapter.jetty :only [run-jetty]])
  (:require [midje.repl :refer [autotest]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :refer [html]]
            [hiccup.bootstrap.middleware :refer [wrap-bootstrap-resources]]
            [technote.views.default      :refer [default-page four-oh-4]]
            [technote.views.workorder    :refer [new-workorder
                                                 workorder
                                                 workorder-add
                                                 list-workorders]]
            [technote.views.login        :refer [login authenticate-user]]))

;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes

  ; to serve document root address
  (GET "/" [] (default-page [:div [:a {:href "/login"} "login"]]
                            [:div [:a {:href "/workorder"} "workorder"]]
                            [:div [:a {:href "/workorder/new"} "new workorder"]]
                            [:div [:a {:href "/workorder/42"} "workorder number 42"]]))

  ; login related pages
  (GET  "/login" [] (login))
  (POST "/login" [user password] (authenticate-user user password))

  ; workorder related pages
  (GET "/workorder"                   [] (list-workorders))
  (GET "/workorder/new"               [] (new-workorder))
  (GET ["/workorder/:id" :id #"\w+"]  [id] (workorder id))
  (POST "/workorder-add"              [& stuff] (workorder-add stuff))

  ; to serve static pages saved in resources/public directory
  (route/resources "/")

  (route/not-found four-oh-4))

(def handler
  (wrap-bootstrap-resources (handler/site app-routes)))

(def server (run-jetty handler {:port 3000 :join? false}))
