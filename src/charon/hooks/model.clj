(ns charon.hooks.model
  (:require [caribou.hooks :as hooks]))

;; a model hook receives a map 'env' as its sole arg and returns that map,
;; potentially modified.
;; The env map has keys that contain information about the operation underway.

;; :content - the thing hooked
;; :model - the relevant model
;; :values - arguments to the function spawning hook - modify this to change
;;           what actually gets stored
;; :op - the operation spawning the hook (mainly used for the save hooks)
;; :opts - optional opts arg to model/create
;; :spec - for create hooks, describes how one would update this row
;; :original - for update hooks, the object before the operation in question

;; creation hooks in order called
:before-save
:before-create
:after-create
:after-save

;; update hooks in order called
:before-save
:before-update
:after-update
:after-save

;; destroy hooks in order called
:before-destroy
:after-destroy

(defn add-hooks
  []
  (hooks/add-hook
   :model :before-destroy
   ;; the third argument is a unique key, by calling add-hook
   ;; again with a duplicate key, you can replace that hook, by
   ;; providing a unique key you can add an additional hook
   :reinitialize
   (fn [env]
     (println "this hook never runs")
     env))

  (hooks/add-hook
   :model :before-destroy :reinitialize ; replacing the above hook
   (fn [env]
     (println "a moment of reflection for our departing model --")
     env)))
