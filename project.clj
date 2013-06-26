(defproject technote "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.2.0"

  ;; CLJ source code path
  :source-paths ["src/clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.3"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.2.0-RC1"]
                 [com.novemberain/monger "1.5.0"]
                 [hiccup-bootstrap "0.1.2"]]

  ;; lein-cljsbuild plugin to build a CLJS project
  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-ring "0.8.5"]]

  :profiles { :dev
              { :dependencies [[midje "1.5.1"]] }
              :production
              {}}

  :ring {:handler technote.core/handler}

  ;; cljsbuild options configuration
  :cljsbuild {:builds
              [{;; CLJS source code path
                :source-paths ["src/cljs"]

                ;; Google Closure (CLS) options configuration
                :compiler {;; CLS generated JS script filename
                           :output-to "resources/public/js/technote.js"

                           ;; minimal JS optimization directive
                           :optimizations :whitespace

                           ;; generated JS code prettyfication
                           :pretty-print true}}]})
