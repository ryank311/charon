(ns charon.migrations.default
  (:require [caribou.model :as model]))

(defn migrate
  []
  (model/invoke-models)
  (let [site (model/create :site {:name "Site" :description "Frontend"})]
    (model/create :page {:name "Home"
                         :path ""
                         :controller "home"
                         :action "home"
                         :template "home.html"
                         :site_id (:id site)})))

(defn rollback
  [] 
  (if-let [default-page (model/pick :page {:where {:name "Home"}})]
    (model/destroy :page (:id default-page))))
