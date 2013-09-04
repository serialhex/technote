(ns technote.views.customer
  (:require [clojure.string :as string]
            [hiccup.core :refer [html]]
            [technote.views.default :refer [default-page]]
            [technote.customer :refer [all-customers]]))

(defn list-customers []
  (default-page
    (map (fn [cust]
      [:div (str cust)
        [:br]])
      (all-customers))))
