(ns technote.database
  (:use (korma core db))
  (:require [clojure.string :as str]))

; some info:
; http://byatool.com/clojure/setting-up-sqlkorma-with-postgresql-on-windows-7/

(defn insert-stuff [& stuff]
  (println stuff))
(defn get-stuff [& stuff]
  (println stuff))
(defn get-workorder [& stuff]
  (println stuff))
(defn update-workorder [& stuff]
  (println stuff))


(defdb db (postgres
            {:db "technote-dev"
             :user "technote"
             :password "technote"}))

(declare customers techs workorders phone-number address)

(defentity customers
  (entity-fields  :cust-name
                  :company-name
                  :salutation
                  :first-name
                  :middle-name
                  :last-name)
  (has-many workorders phone-number address))

(defentity phone-number
  (entity-fields  :number
                  :type
                  :current?)
  (belongs-to customers))

(defentity address
  (entity-fields  :line1
                  :line2
                  :line3
                  :city
                  :state
                  :zip
                  :country
                  :current?)
  (belongs-to customers))

(defentity workorders
  (entity-fields :content)
  (belongs-to customers))

(defentity techs
  (entity-fields :name :misc)
  (many-to-many workorders :tech-workorders))
