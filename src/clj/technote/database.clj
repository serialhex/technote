(ns technote.database
  (:use [korma.db]
        [korma.core]))

; [:company :name :street :city :zip :phone :problems]

(defdb db (postgres {:db "technote-dev"
                     :user "postgres"
                     :password "postgres"}))

