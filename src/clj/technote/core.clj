(ns technote.core
  (:use compojure.core)
  (:require [midje.repl :refer [autotest]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [technote.routes :refer [handler]]))

(defonce server (run-jetty handler {:port 8080 :join? false}))

(defn app-start []
  (.start server))

(defn app-stop []
  (.stop server))

(defn app-reset []
  (do
    (println "\nstopping server")
    (app-stop)
    (println "\nrefreshing")
    (refresh :after 'technote.core/app-start)))


; (use '[clojure.tools.namespace.repl :only (refresh)])
; (refresh)
