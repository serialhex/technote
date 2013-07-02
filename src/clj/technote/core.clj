(ns technote.core
  (:use compojure.core)
  (:require [midje.repl :refer [autotest]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [technote.routes :refer [handler]]))

; (defonce server (run-jetty handler {:port 8080 :join? false}))

; (defn app-start []
;   (.start server))

; (defn app-reset []
;   (do
;     (println "\nstopping server")
;     (.stop server)
;     ; i don't want this, but if i don't it dosn't work right...
;     (Thread/sleep 1000)
;     (println "\nrefreshing")
;     (refresh)
;     (Thread/sleep 1000)
;     (println "\nstarting app")
;     (app-start)))
