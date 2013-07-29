(ns lobos.config
  (:use lobos.connectivity))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user "postgres"
   :password "postgres"
   :subname "//localhost:5432/spoon"})

(open-global db)
