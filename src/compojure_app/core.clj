(ns compojure-app.core
   (:require
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as response]
   [ring.middleware.reload :refer [wrap-reload]]
   [compojure.core :refer :all]
   [compojure.route :as route]))

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
      (wrap-reload #'app)
      {:port 3000
       :join? false}))))

