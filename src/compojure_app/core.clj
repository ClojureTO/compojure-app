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
   [hiccup.core :refer :all]
   [hiccup.page :refer :all]
   [selmer.parser :refer :all]
   [environ.core :refer [env]]
   [selmer.middleware :refer [wrap-error-page]]))

;;handlers
(defn home []
  (-> (html5
       [:head
        (include-css "/css/site.css")]
       [:body
        [:h1 "Hello World"]
        [:p "this is a test"]])
      (response/response)
      (response/content-type "text/html")))

(defn html-page []
  (render-file "templates/about.html"
               {:people
                [{:name "Bob"}
                 {:name "Alice"}]}))

;;routes
(defroutes app
  (GET "/" [] (home))
  (GET "/about" [] (html-page))
  (route/not-found "<h1>Page not found</h1>"))

;;middleware
(defn wrap-dev [handler]
  (if (env :dev)
    (-> handler wrap-reload wrap-error-page)
    handler))

;;server
(defonce server (atom nil))

(defn stop-server []
  (when-let [instance @server]
    (.stop instance)
    (reset! server nil)))

(defn start-server []
  (when-not @server
    (when (env :dev)
      (selmer.parser/cache-off!))
    (reset!
     server
     (jetty/run-jetty
      ;; the #'app ensures that app is
      ;; reloaded when routes change
      (-> #'app
          wrap-dev
          (wrap-resource "public")
          wrap-content-type
          wrap-not-modified)
      {:port 3000
       :join? false}))))

