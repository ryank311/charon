(ns charon.controllers.home
  (:use [schmetterling.core :only (debugger)])
  (:require [caribou.model :as model]
            [caribou.app.controller :as controller]))

(defn home
  [request]
  (controller/render 
   (assoc request 
     :verbed "Started")))

