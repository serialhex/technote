(ns technote.misc)

(defn map-split
  "Takes a map of values & spits out a map with only
  the values associated to the provided keys"
  [vals & keys]
  (reduce (fn [mp ke]
    (merge {ke (ke vals)} mp)) {} keys))
