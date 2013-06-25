(ns technote.database
  (:require [monger.core       :refer [connect! set-db! get-db]]
            [monger.collection :refer [insert-and-return insert-batch]])
  (:import [org.bson.types ObjectId]))

; [:company :name :street :city :zip :phone :problems]

(connect!)
; set default db - the test one!
(set-db! (get-db "tech-test"))

(defn insert-stuff [stuff]
  (insert-and-return "document" (merge {:_id (ObjectId.)} stuff)))

