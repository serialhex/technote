(ns technote.views.login
  (:require [hiccup.core :refer [html]]
            [technote.views.default :refer (default-page)]))

(defn login []
  (default-page
    [:form {:action "login"
            :method "post"
            :id     "login-form"}
      [:fieldset

        [:div
          [:label {:for "user"} "Tech Login"]
          [:input#user {:type "text"
                        :name "user"
                        :required ""}]]

        [:div
          [:label {:for "password"} "Password"]
          [:input#password {:type "password"
                            :name "password"
                            :required ""}]]
        [:div
          [:label {:for "submit"}]
          [:input#submit {:type "submit" :value "Login ->"}]]]]))

(defn authenticate-user [user password]
  (default-page
    [:div
      [:p "You posted " [:b user] " as your user and " [:b password] " as your password."]]))
