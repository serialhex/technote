(ns technote.workorder
  (:require [monger.core        :as mc]
            [monger.collection  :as coll]
            [monger.query       :as q]
            [technote.database  :as db])
  (:import [org.bson.types ObjectId]))

; http://docs.mongodb.org/manual/tutorial/create-an-auto-incrementing-field/