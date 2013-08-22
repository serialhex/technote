(ns technote.misc)

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
