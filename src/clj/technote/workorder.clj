;;  this is all probably getting nixed here in a few moments...


(ns technote.workorder
  (:require [monger.core        :as mc]
            [monger.collection  :as coll]
            [monger.query       :as q]
            [technote.database  :as db])
  (:import [org.bson.types ObjectId]))

; http://docs.mongodb.org/manual/tutorial/create-an-auto-incrementing-field/

(defn insert-stuff [stuff]
  (insert-and-return "document" (merge {:_id (ObjectId.)} stuff )))

(defn get-stuff []
  (q/with-collection "document"
    (q/find {})
    (q/sort {})
    (q/paginate :page 1 :per-page 10)))

(defn get-workorder [id]
  (first
    (q/with-collection "document"
      (q/find {:_id (ObjectId. id)}))))

(defn update-workorder [id upd]
  (update-by-id "document" (ObjectId. id) {$push (first upd)} :upsert true))
