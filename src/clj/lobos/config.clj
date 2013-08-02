(ns lobos.config
  (:use lobos.connectivity))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user "technote"
   :password "technote"
   :subname "//localhost:5432/technote-dev"})

(open-global db)
