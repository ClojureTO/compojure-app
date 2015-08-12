(defproject compojure-app "0.1.0-SNAPSHOT"
  :description "sample Compojure project"
  :url "https://github.com/ClojureTO/compojure-app"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]]
  :main compojure-app.core/start-server)
