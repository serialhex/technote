(ns technote.customer
  (:require [technote.database :as db]
            [korma.core :refer [insert values]]
            ; [clojure.string :as str]
            ))

(declare add-phone-number add-address)

(defn new-customer [info]
  (let [{:keys
          [company-name
           first-name
           last-name
           middle-name
           phone-number
           address]}
        info]
    (let [cust
          (insert db/customers
            (values {:cust-name (str last-name ", " first-name)
                     :first-name first-name
                     :last-name last-name
                     :middle-name middle-name}))]
          (map #(add-phone-number (:id cust) %) phone-number)
          (add-address (:id cust) address)))


; isn't there a better way than these 2 functions???
(defn add-phone-number [customer-id phone-number]
  (insert db/phone-number
    (values (assoc phone-number
             :current? true
             :customer_id customer-id)))))

(defn add-address [customer-id address]
  (insert db/address
    (values (assoc address
             :current? true
             :customer_id customer-id))))
