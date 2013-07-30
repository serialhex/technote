(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema
               config helpers)))

(defmigration add-customers-table
  (up [] (create
          (tbl :customers
            (varchar :name 209 :unique :not-null)
            )))
  (down [] (drop (table :customers))))

(defmigration add-techs-table
  (up [] (create
          (tbl :techs
            (varchar :name 200 :unique :not-null)
            (text :misc))))
  (down [] (drop (table :techs))))

(defmigration add-workorders-table
  (up [] (create
          (tbl :workorders
            (text :content)
            (refer-to :customers))))
  (down [] (drop (table :workorders))))

(defmigration add-technician-workorders-table
  (up [] (create
          (tbl :tech-workorders
            (refer-to :techs)
            (refer-to :workorders)))))

;; run migrations
; (binding [lobos.migration/*src-directory* "src/clj/"] (lobos.core/migrate))
