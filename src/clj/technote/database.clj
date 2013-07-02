(ns technote.database
  (:require [monger.core       :as mc   :refer [connect! set-db! get-db]]
            [monger.collection :as coll :refer [insert-and-return insert-batch update-by-id]]
            [monger.query      :as q])
  (:use monger.operators)
  (:import [org.bson.types ObjectId]))

; [:company :name :street :city :zip :phone :problems]

(connect!)
; set default db - the test one!
(set-db! (get-db "tech-test"))

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
