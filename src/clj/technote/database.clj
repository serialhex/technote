(ns technote.database
  (:use [korma.db]
        [korma.core]))

; [:company :name :street :city :zip :phone :problems]

(defdb db (postgres {:db "technote-dev"
                     :user "postgres"
                     :password "postgres"}))

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
