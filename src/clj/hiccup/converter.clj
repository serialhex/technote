(ns hiccup.converter
  (:use [clojure.data.xml :only (parse)]
        [clojure.pprint :only (pprint)])
  (:import (java.io File)))
; mostly stolen from
; http://nakkaya.com/2009/11/23/converting-html-to-compojure-dsl/

(defn format-attrs
  [m]
  (when m
    (format "%s" m)))

(defn empty-when-null
  [x]
  (if (nil? x)
    ""
    x))

(declare format-full-node)

(defn format-node
  [node]
  (cond
   (string? node) (format "\"%s\"" (.trim node))
   (nil? node) nil
   :else (format-full-node node)))

(defn format-full-node
  [node]
  (format "[%s %s %s]\n"
          (:tag node)
          (empty-when-null (format-attrs (:attrs node)))
          (clojure.string/join " " (map format-node (:content node)))))

(defn transform-str
  [str]
  (->> str
       java.io.StringReader.
       parse
       format-node
       read-string
       pprint
       print))

(defn transform-file [f-name]
  (transform-str (slurp f-name)))
