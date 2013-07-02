(ns technote.views.workorder
  (:require [hiccup.core :refer [html]]
            [technote.views.default :refer [default-page]]
            [technote.database :refer [insert-stuff
                                       get-stuff
                                       get-workorder
                                       update-workorder]]))

(defn new-workorder []
  (default-page
    [:form {:action "/workorder-add"
            :method "post"
            :id     "new-workorder-form"}
      [:fieldset
        (map (fn [[info argz]]
                [:div
                  [:label {:for info} (clojure.string/capitalize info)]
                  [:input (merge {:type "text" :name info} argz)]])
          { "company" {}
            "name"    {:required ""}
            "street"  {:required ""}
            "city"    {:required ""}
            "zip"     {:required ""}
            "phone"   {:required ""}})
        [:div
          [:label {:for "problems"} "Problems - wonky css... :'("]
            [:textarea#problems {:name "problems"
                                 :rows 8
                                 :cols 50
                                 :required ""}]]
        [:div
          [:label {:for "submit"}]
          [:input#submit {:type "submit" :value "Get 'er dunn!!"}]]]]))

(defn workorder-add [stuff]
  (default-page
    (let [data (insert-stuff stuff)]
      [:div
        (str "Thank you " (:name data) ".  "
             "Your workorder number is: " (:_id data) ".  "
             "We will contact you once we know anything.")
        [:br]
        [:a {:href "/"} "home"]])))

(defn workorder [id & upd]
  (if (not (nil? upd))
    (update-workorder id upd))
  (default-page
    (let [wo (get-workorder id)
          company (str (:company wo))
          custname (str (:name wo))
          phone (:phone wo)
          problems (:problems wo)
          work-done (:work-done wo)]
      [:table
        [:tr
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
            [:td]
            [:td
              (if (not-empty work-done)
                (reverse
                  (map (fn [note]
                            [:div.work
                              [:p "work done: " note]])
                    work-done)))
                  ]]])))

(defn list-workorders []
  (default-page
    [:div
      (map (fn [workord]
        (let [{company  :company
               custname :name
               number   :_id
               } workord]
          [:div
            (str custname " "
              (if (not-empty company)
                (str "from " company " ")))
            [:a {:href (str "/workorder/" number)} "view"]
            [:br]]))
        (get-stuff))]))
