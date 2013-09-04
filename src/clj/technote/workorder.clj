(ns technote.workorder
  (:require [technote.database :as db]
            [technote.misc :as misc]
            [technote.customer :as cust]
            [korma.core :refer :all]))

(defrecord Workorder
  [customer
   work-done
   ])

(defrecord WorkDone
  [stuff])

(defn new-workorder [input]
  (let [data (first input)]
    (prn input)
    (let [cust
          (cust/get-cust-id data)]
      (insert db/workorders
        (values { :customer_id (:customer-id cust)
                  :problem (:problems data)})))))



(defn get-stuff [num-off]
  (select db/workorders
    (with db/customers)
    (order :id :DESC)
    (limit 10)
    (offset num-off)))


(defn get-workorder [id]
  (prn "in tk/wo/gw, args: " id)
  (first (select db/workorders
          (with db/work-performed)
          (with db/customers)
          (where {:id (Integer. id)}))))

(defn update-workorder [id stuff]
  (prn "in tk/wo/uw got: " id ", " stuff)
  (insert db/work-performed
    (values {:workorder_id (Integer. id)
             :work (:work-done stuff)})))
