(ns technote.core
  (:use compojure.core)
  (:require [midje.repl :refer [autotest]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [technote.routes :refer [handler]]
            [technote.qbclient :refer [connect disconnect]]))

(defonce server (run-jetty handler {:port 8080 :join? false}))
(def qb-server {:name "192.168.1.47" :port 3000})
(def qb-connection (ref []))

(defn app-start []
  (connect qb-server qb-connection)
  (.start server))

(defn app-stop []
  (disconnect qb-connection)
  (.stop server))

(defn app-reset []
  (do
    (println "\nstopping server")
    (app-stop)
    (println "\nrefreshing")
    (refresh :after 'technote.core/app-start)))


; (use '[clojure.tools.namespace.repl :only (refresh)])
; (refresh)
