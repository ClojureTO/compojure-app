(ns compojure-app.core
  (:require
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as response]
   [ring.middleware.reload :refer [wrap-reload]]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.middleware.content-type :refer [wrap-content-type]]
   [ring.middleware.not-modified :refer [wrap-not-modified]]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [hiccup.core :refer :all]))

(defn home []
  (-> "<h1>Hello World</h1>"
      (response/response)
      (response/content-type "text/html")))

(defroutes app
  (GET "/" [] (home))
  (GET "/about" [] "hello")
  (route/not-found "<h1>Page not found</h1>"))

(defonce server (atom nil))

(defn stop-server []
  (when-let [instance @server]
    (.stop instance)
    (reset! server nil)))

(defn start-server []
  (when-not @server
    (reset!
     server
     (jetty/run-jetty
      ;; the #'app ensures that app is
      ;; reloaded when routes change
      (-> #'app
          wrap-reload
          (wrap-resource "public")
          wrap-content-type
          wrap-not-modified)
      {:port 3000
       :join? false}))))

