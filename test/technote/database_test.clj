(ns technote.database-test
  (:use midje.sweet
        technote.database))

(fact "I stick stuff in my database!!!"
  (insert-stuff {:foo "bar"}) => truthy
  (nil? (:_id (insert-stuff {:foo "bar"}))) => falsey)