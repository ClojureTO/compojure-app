(defproject compojure-app "0.1.0-SNAPSHOT"
  :description "sample Compojure project"
  :url "https://github.com/ClojureTO/compojure-app"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [selmer "0.9.1"]
                 [environ "1.0.0"]]

  :main compojure-app.core/start-server

  :plugins [[lein-environ "1.0.0"]]

  :profiles
  ;;dev is a composite profile combining
  ;; :profiles/dev and project/dev profiles
  ;; :profiles/dev should be defined in profiles.clj
  {:dev [:project/dev :profiles/dev]
   :project/dev
   {:env
    {:dev true}}})
