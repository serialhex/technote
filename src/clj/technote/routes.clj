(ns technote.routes
  (:use compojure.core
        [ring.adapter.jetty :only [run-jetty]])
  (:require [midje.repl :refer [autotest]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :refer [html]]
            [hiccup.bootstrap.middleware :refer [wrap-bootstrap-resources]]
            [technote.views.default      :refer [default-page four-oh-4]]
            [technote.views.workorder    :refer [workorder-new
                                                 workorder
                                                 workorder-add
                                                 list-workorders]]
            [technote.views.login        :refer [login authenticate-user]]
            [technote.views.customer     :refer [list-customers]]))

;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes

  ; to serve document root address
  (GET "/" [] (list-workorders 0))

  ; login related pages
  (GET  "/login" [] (login))
  (POST "/login" [user password] (authenticate-user user password))

  ; workorder related pages
  (GET "/workorder"                     [offset] (list-workorders offset))
  (GET "/workorder/new"                 [] (workorder-new))
  (POST "/workorder/new"                [& stuff] (workorder-new stuff))
  (GET ["/workorder/:id" :id #"\w+"]    [id] (workorder id))
  (POST ["/workorder/:id" :id #"\w+"]   [id & upd] (workorder id upd))

  ; customer related pages
  (GET "/customer" [] (list-customers))

  ; to serve static pages saved in resources/public directory
  (route/resources "/")

  (route/not-found four-oh-4))

(def handler
  (wrap-bootstrap-resources (handler/site app-routes)))

; (defonce server (run-jetty handler {:port 8080 :join? false}))
; (defn kill-server [] (.stop (run-jetty handler {:port 8080 :join? false})))
