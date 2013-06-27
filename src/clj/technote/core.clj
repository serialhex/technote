(ns technote.core
  (:use compojure.core)
  (:require [midje.repl :refer [autotest]]
            [technote.routes :refer [server]]))


(defn app-start []
  (autotest)
  (.start server))
