(ns charon.routes
  (:require [caribou.app.pages :as pages]))

(def routes
  [["/" :home {:GET {:controller 'home :action 'home :template "home.html"}}]])

(defn build-routes
  [routes namespace]
  (pages/bind-actions routes namespace))

(defn gather-pages
  []
  (try 
    (pages/all-pages)
    (catch Exception e nil)))
