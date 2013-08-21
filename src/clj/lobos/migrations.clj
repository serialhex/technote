(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema
               config helpers)))

(defmigration add-customers-table
  (up [] (create
          (tbl :customers
            (varchar :cust-name 41 :unique :not-null)
            (varchar :company-name 41)
            (varchar :salutation 15)
            (varchar :first-name 20)
            (varchar :middle-name 5)
            (varchar :last-name 20))))
  (down [] (drop (table :customers))))

(defmigration add-phone-number-table
  (up [] (create
          (tbl :phone-number
            (varchar :number 15)
            (varchar :type 21)
            (boolean :current?)
            (refer-to :customers))))
  (down [] (drop (table :phone-number))))

(defmigration add-address-table
  (up [] (create
          (tbl :address
            (varchar :line1 41)
            (varchar :line2 41)
            (varchar :line3 41)
            (varchar :city 31)
            (varchar :state 21)
            (varchar :zip 13)
            (varchar :country 31)
            (boolean :current?)
            (refer-to :customers))))
  (down [] (drop (table :address))))

(defmigration add-techs-table
  (up [] (create
          (tbl :techs
            (varchar :name 200 :unique :not-null)
            (text :misc)
            )))
  (down [] (drop (table :techs))))

(defmigration add-workorders-table
  (up [] (create
          (tbl :workorders
            (text :problem)
            (refer-to :customers)
            )))
  (down [] (drop (table :workorders))))

; a many-to-many relationship between technicians and workorders
(defmigration add-technician-workorders-table
  (up [] (create
          (tbl :tech-workorders
            (refer-to :techs)
            (refer-to :workorders)
            )))
  (down [] (drop (table :tech-workorders))))

(defmigration add-customer-workorders-table
  (up [] (create
          (tbl :customer-workorders
            (refer-to :customers)
            (refer-to :workorders)
            )))
  (down [] (drop (table :customer-workorders))))

(defmigration add-work-performed-table
  (up [] (create
          (tbl :work-performed
            (text :work)
            (refer-to :techs)
            (refer-to :workorders)
            (boolean :show-customer))))
  (down [] (drop (table :work-performed))))

;; run migrations
; (binding [lobos.migration/*src-directory* "src/clj/"] (lobos.core/migrate))

;; rollback migrations
; (binding [lobos.migration/*src-directory* "src/clj/"] (lobos.core/rollback :all))
