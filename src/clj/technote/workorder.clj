(ns technote.workorder
  (:require [technote.database :as db]
            [technote.misc :as misc]
            [technote.customer :as cust]
            [korma.core :refer :all]))


(defn new-workorder [input]
  (let [data (first input)]
    (let [cust-id
          (cust/new-customer data)]
      (insert db/workorders
        (values { :customer_id cust-id
                  :problem (:problems data)})))))



(defn get-stuff [num-off]
  (select db/workorders
    (with db/customers)
    (limit 3)
    (offset num-off)))


(defn get-workorder [id]
  (select db/workorders
    (with db/work-performed)
    (with db/customers)
    (where {:id (Integer. id)})))

(defn update-workorder [stuff]
  (println stuff))
