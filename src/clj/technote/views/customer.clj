(ns technote.views.customer
  (:require [clojure.string :as string]
            [hiccup.core :refer [html]]
            [hiccup.element :refer [unordered-list
                                    link-to]]
            [technote.views.default :refer [default-page]]
            [technote.customer :refer [all-customers
                                       get-customer]]
            [technote.workorder :refer [find-workorders]]))

(defn list-customers []
  (default-page
    (unordered-list
      (map #(link-to (str "/customer/" (:customer-id %)) (str %))
        (all-customers)))))

(defn customer [id]
  (let [id (Integer. id)]
    (default-page
      (str (get-customer id))
      [:br]
      (->>  (find-workorders id)
            (map #(link-to (str "/workorder/" (:id (:workorder %))) (:info %)))
            (unordered-list)))))


;; technote.database=> (exec-raw ["SELECT \"cust-name\" FROM customers WHERE \"cust-name\" % 'bob';"] :results)
;; [{:cust-name "Builder, Bob"}]
