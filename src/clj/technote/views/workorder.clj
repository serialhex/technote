(ns technote.views.workorder
  (:require [clojure.string :as string]
            [hiccup.core :refer [html]]
            [hiccup.form :refer :all]
            [technote.views.default :refer [default-page]]
            [technote.workorder :refer [new-workorder
                                        get-stuff
                                        get-workorder
                                        update-workorder]]
            [technote.misc :refer [states do-md]]))

(defn workorder-new
  "Sets up new workorder."
  [& info]
  (if (not (nil? info))

    ;got info to post
    (default-page
      (let [data (new-workorder info)]
        (println "in tk/vi/wo" data)
        [:div
          (str "Thank you " (:name (:cust_id data)) ".  "
               "Your workorder number is: " (:id data) ".  "
               "We will contact you once we know anything.")
          [:br]
          [:a {:href "/"} "home"]]))

    ; getting info to post
    (default-page
      (form-to  [:post "/workorder/new"]
                ; Customer info
                (label "company-name" "Company Name: ")
                  (text-field "company-name")
                  [:br]
                (label "first-name" "First Name: ")
                  (text-field "first-name")
                  [:br]
                (label "last-name" "Last Name: ")
                  (text-field "last-name")
                  [:br]
                (label "street" "Street: ")
                  (text-field "street")
                  [:br]
                (label "city" "City: ")
                  (text-field "city")
                  [:br]
                (label "state " "State: ")
                  (drop-down "state" states 'FL)
                (label {:style "width: 65px;"}
                    "zip" "Zip: ")
                  (text-field {:style "width: 75px;"
                    :maxlength 5} "zip")
                  [:br]
                (label "phone-number" "Phone Number: ")
                  (text-field "phone-number")
                  [:br]

                ; Email info
                (label "email" "Email: ")
                  (text-field "email")
                  [:br]
                (label "email-pass" "Email Password **DANGER!!!!**: ")
                  (text-field "email-pass")
                  [:br]

                ; Computer info
                (label "computer-model" "Computer Model: ")
                  (text-field "computer-model")
                  [:br]
                (label "computer-password" "Computer Password **DANGER!!!!**: ")
                  (text-field "computer-password")
                  [:br]
                (label "computer-description" "Computer description: ")
                  (text-area "computer-description")
                  [:br]

                ; Problems
                (label "problems" "Problems: ")
                  (text-area {:rows 8 :cols 50} "problems")
                  [:br]
                (submit-button "Get 'er dunn!!")))
  ))

; refactoring...
(defn workorder-add
  "Gets info & sends information to be inserted into the database."
  [stuff]
  )

(defn workorder
  "Gets a specific workorder"
  [id & upd]
  (if upd
    (update-workorder id (first upd)))
  (default-page
    (let [wo (get-workorder id)
          company (str (:company wo))
          custname (str (:cust-name wo))
          phone (:phone wo)
          problems (:problem wo)
          work-done (:work-performed wo)]
      [:div
        [:table [:tr
        [:td
          [:div.panel
            (if (not-empty company)
              [:p "Company: " company])
            [:p "Name: " custname]
            [:p "Phone: " phone]]
          [:div.panel
            [:p "Problems: " problems]]]
        [:td
          [:div.work-done
            [:form {:action (str "/workorder/" id)
                    :method "post"
                    :id     "update-workorder"}
              [:label {:for "work-done"} "Work Done: "]
              [:textarea#work-done {:name "work-done"
                                    :rows 4
                                    :cols 80
                                    :required ""}]
              [:label {:for "submit"}]
                [:input#submit {:type "submit" :value "Get 'er dunn!!"}]]]]]

        [:tr
          ; [:td]
          [:td {:colspan 2}
            (if work-done
              (reverse
                (map (fn [note]
                          [:div.work "work done: "
                            [:div.work-done
                              (do-md (:work note))]])
                  work-done)))
                ]]]
        [:div {:hidden "true"} [:p (str wo)]]])))

(defn list-workorders
  "Lists available workorders."
  [offset]
  (let [offset (if offset (Integer. offset) 0)]
    (default-page
      [:div
        (map (fn [workord]
          (let [{company  :company
                 custname :cust-name
                 number   :id}
                 workord]
            [:div
              (str "cust: " custname " "
                (if (not-empty company)
                  (str "from " company " "))
                "workorder number: " number " ")
              [:a {:href (str "/workorder/" number)} "view"]
              [:br]]))
          (get-stuff offset))]
      [:div
        (if (> offset 0)
          [:a {:href (str "/workorder?offset=" (- offset 10))}
              "get prev"])
        " "
        [:a {:href (str "/workorder?offset=" (+ 10 offset))}
              "get next"]
        ])))
