(ns technote.database
  (:require [clojure.string :as str]
            [korma [core :refer :all] [db :refer :all]]))

; some info:
; http://byatool.com/clojure/setting-up-sqlkorma-with-postgresql-on-windows-7/

(defdb db (postgres
            {:db "technote-dev"
             :user "technote"
             :password "technote"}))

(declare customers techs workorders phone-number address work-performed email computer)

(defentity customers
  (entity-fields  :cust-name
                  :company-name
                  :salutation
                  :first-name
                  :middle-name
                  :last-name)
  (has-many phone-number)
  (has-many address)
  (has-many workorders)
  (has-many email)
  (has-many computer))

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

(defentity email
  (entity-fields  :email
                  :password)
  (belongs-to customers))

(defentity computer
  (entity-fields  :model
                  :description
                  :password)
  (belongs-to customers)
  (belongs-to workorders))

(defentity workorders
  (entity-fields :problem)
  (has-many work-performed {:fk :workorder_id})
  (belongs-to customers {:fk :customer_id}))

(defentity work-performed
  (entity-fields :work
                 :show_customer)
  (belongs-to techs)
  (belongs-to workorders {:fk :workorder_id}))

(defentity techs
  (entity-fields :name :misc)
  (many-to-many workorders :tech-workorders
    {:lfk :tech_id
     :rfk :workorder_id}))
