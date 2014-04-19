(ns technote.customer
  (:require [technote.database :as db]
            [technote.misc :refer [map-split]]
            [korma.core :refer :all]))

(declare  add-phone-number add-address new-customer
          get-phone-numbers get-addresses get-emails get-computers)

(defrecord Customer
  [company-name
   first-name
   last-name
   phone-numbers
   addresses
   emails
   computers
   customer-id]
  Object
  (toString [_] (str first-name " " last-name)))

(defrecord PhoneNumber
  [phone-number
   current?
   type])

(defrecord Address
  [line1
   line2
   line3
   city
   state
   zip
   country
   current?])

(defrecord Email
  [email
   password])

(defrecord Computer
  [model
   description
   password
   workorder])

(defn cust-record [cust]
  (Customer.  (:company-name cust)
              (:first-name cust)
              (:last-name cust)
              (get-phone-numbers (:id cust))
              (get-addresses (:id cust))
              (get-emails (:id cust))
              (get-computers (:id cust))
              (:id cust)))

(defn get-customer
  "Gets customer from database by id & returns record of customer"
  [id]
  (let [cust (first ;; and only...
              (select db/customers
                (where {:id id})))]
    (if cust
      (cust-record cust)
      (println "There is no customer with ID:" id))))

(defn get-phone-numbers
  "Gets a list of phone numbers for customer"
  [cust-id]
  (let [numbers (select db/phone-number
                  (order :created_on)
                  (where {:customer_id cust-id}))]
    (map (fn [n]
      (PhoneNumber. (:number n)
                    (:current? n)
                    (:type n)))
      numbers)))

(defn get-addresses
  "Gets a list of addresses for customer"
  [cust-id]
  (let [addresses (select db/address
                    (order :created_on)
                    (where {:customer_id cust-id}))]
    (map (fn [a]
      (Address. (:line1 a)
                (:line2 a)
                (:line3 a)
                (:city a)
                (:state a)
                (:zip a)
                (:country a)
                (:current? a)))
      addresses)))

(defn get-emails
  "Gets a list of email addresses for customer"
  [cust-id]
  (let [email (select db/email
                    (order :created_on)
                    (where {:customer_id cust-id}))]
  (map #((Email.  (:email %)
                  (:password %)))
    email)))

(defn get-computers
  "Gets a list of computers for current customer"
  [cust-id]
  (let [computer (select db/computer
                    (order :created_on)
                    (where {:customer_id cust-id}))]
  (map #((Computer. (:model %)
                    (:description %)
                    (:password %)
                    (:workorder %)))
    computer)))

(defn all-customers []
  (->>  (select db/customers
          (order :cust-name))
        (map #(cust-record %))))

(defn lookup-customer [cname]
  "Finds customer, returns id."
  (let [cust (select db/customers
              (fields :id :cust-name)
              (where {:cust-name [like cname]}))]
    (prn cust)
    (get-customer (:id (first cust)))))

(defn get-cust-id [info]
  (let [{:keys
          [first-name
           last-name]}
        info]
    (if (empty? (lookup-customer (str last-name ", " first-name)))
      (new-customer info)
      (lookup-customer (str last-name ", " first-name)))))

(defn new-customer [info]
  "Sets up new customer, returns customer id."
  (println "in cu/nc" info)
  (let [{:keys
          [company-name
           first-name
           last-name
           middle-name
           phone-number]}
        info]
    (let [cust
          (insert db/customers
            (values {:cust-name (str last-name ", " first-name)
                     :first-name first-name
                     :last-name last-name
                     :middle-name middle-name}))]
          (add-phone-number (:id cust) phone-number)
          (add-address (:id cust) (map-split info :street :city :zip))
          (get-customer (:id cust)))))

; isn't there a better way than these 2 functions???
(defn add-phone-number [customer-id phone-number]
  (println "add phone-number: " customer-id)
  (insert db/phone-number
    (values (assoc {:number phone-number}
             :current? true
             :customer_id customer-id))))

(defn add-address [customer-id address]
  (println "add address: " customer-id address)
  (insert db/address
    (values {:line1 (:street address)
             :city (:city address)
             :zip (:zip address)
             :current? true
             :customer_id customer-id})))

#_(defn add-email [cust-id email password]
  (insert db/email
    (values {:customer_id cust-id
             :email email
             :password password})))
