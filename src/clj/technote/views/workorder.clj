(ns technote.views.workorder
  (:require [clojure.string :as string]
            [hiccup.core :refer [html]]
            [technote.views.default :refer [default-page]]
            [technote.workorder :refer [new-workorder
                                        get-stuff
                                        get-workorder
                                        update-workorder]]))

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
      [:form {:action "/workorder/new"
              :method "post"
              :id     "new-workorder-form"}
        [:fieldset
          (map (fn [[info argz]]
                  [:div
                    [:label {:for info} (string/capitalize (string/replace info "-" " "))]
                    [:input (merge {:type "text" :name info} argz)]])
            { "company-name"  {}
              "first-name"    {:required ""}
              "last-name"     {:required ""}
              "street"        {:required ""}
              "city"          {:required ""}
              "zip"           {:required ""}
              "phone-number"  {:required ""}})
          [:div
            [:label {:for "problems"} "Problems - wonky css... :'("]
              [:textarea#problems {:name "problems"
                                   :rows 8
                                   :cols 50
                                   :required ""}]]
          [:div
            [:label {:for "submit"}]
            [:input#submit {:type "submit" :value "Get 'er dunn!!"}]]]])
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
                          [:div.work
                            [:p "work done: " (:work note)]])
                  work-done)))
                ]]]
        [:div [:p (str wo)]]])))

(defn list-workorders
  "Lists available workorders."
  [offset]
  (prn "in tk/vi/lw " offset)
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
        (get-stuff (if offset (Integer. offset) 0)))]
      [:div
        [:a {:href (str "/workorder?offset=" (+ 10 (if offset (Integer. offset) 0)))}
              "get next"]
        ]))
