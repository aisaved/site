
(ns centipair.routes.home
  (:require [centipair.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" ))


(defn services-page [])


(defn projects-page [id title]
  (if (and (= id "592653") (= title "real-estate-web-application-ios-android-mobile-app"))
    (layout/render "project.html")
    (layout/render "404.html")))



(defn projects-home-page []
  (layout/render "projects-home.html" )
  )

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/projects" [] (projects-home-page))
  (GET "/services" [] (services-page))
  (GET "/project/:id/:title" [id title] (projects-page id title)))
