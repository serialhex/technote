(ns technote.misc
  (:require [markdown.core :as md]))

(defn map-split
  "Takes a map of values & spits out a map with only
  the values associated to the provided keys

  (map-split {:foo 'foo :bar 'bar :baz 'baz} :foo :baz)
  => {:foo foo, :baz baz}"
  [vals & keys]
  (reduce (fn [mp ke]
    (merge {ke (ke vals)} mp)) {} keys))

(defmacro unless [pred a b]
  `(if (not ~pred) ~a ~b))

(defn format-time [timestamp]
  (-> "hh:mma 'on' dd MMMM yyyy"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(def states
  ['AL
   'AK
   'AZ
   'AR
   'CA
   'CO
   'CT
   'DC
   'DE
   'FL
   'GA
   'HI
   'ID
   'IL
   'IN
   'IA
   'KS
   'KY
   'LA
   'ME
   'MD
   'MA
   'MI
   'MN
   'MS
   'MO
   'MT
   'NE
   'NV
   'NH
   'NJ
   'NM
   'NY
   'NC
   'ND
   'OH
   'OK
   'OR
   'PA
   'RI
   'SC
   'SD
   'TN
   'TX
   'UT
   'VT
   'VA
   'WA
   'WV
   'WI
   'WY])

(defn newline-br [text {:keys [code lists] :as state}]
  [(if (not (or code lists))
     (str text "<br />")
     text)
   state])

(defn do-md [text]
  (md/md-to-html-string text :custom-transformers [newline-br]))
