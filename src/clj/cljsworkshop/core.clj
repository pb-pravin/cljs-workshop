(ns cljsworkshop.core
  (:require [qbits.jet.server :refer [run-jetty]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.response :refer [render]]
            [clojure.java.io :as io]))

;; Simple function that works as controller
;; It should return a proper response. In our
;; case it returns a content of static index.html.
(defn home
  [req]
  (render (io/resource "index.html") req))

(defn test
  [req]
  (render (io/resource "test.html") req))

(defn test2
  [req]
  (render (io/resource "test.html") req))

;; Routes definition
(defroutes app
  (GET "/" [] home)
  (GET "/test" [] test)
  (GET "/test/:id" [] test2)
  (route/resources "/static")
  (route/not-found "<h1>Page not found</h1>"))

;; Application entry point
(defn -main
  [& args]
  (let [app (wrap-defaults app site-defaults)]
    (run-jetty {:ring-handler app :port 5050})))

