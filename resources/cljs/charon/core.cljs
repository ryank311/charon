(ns charon.core
  (:require [charon.connect :as connect]))

(defn log
  [s]
  (.log js/console s))

(defn slice-nonarray 
  [na]
  (.call (-> js/Array .-prototype .-slice) na))

(defn has-children?
  [el]
  (not (= 0 (-> el .-childNodes .-length))))

(defn get-children
  [el]
  (-> el .-childNodes slice-nonarray))

(defn get-body
  []
  (-> js/document .-body get-children))

(defn text-node?
  [n]
  (= (.-name (type n)) "Text"))

(defn remove-vowels
  [s]
  (let [vowels #{\a \e \i \o \u \A \E \I \O \U}
        removed (remove vowels s)]
    (apply str removed)))

(defn pull-nodes
  [p? els]
  (reduce 
   (fn [text-nodes el]
     (cond 
      (p? el) (conj text-nodes el)
      (has-children? el) (concat text-nodes (pull-nodes p? (get-children el)))
      :else text-nodes))
   [] els))

(defn walk-dom
  [p? f]
  (let [body (get-body)
        pulled (pull-nodes p? body)]
    (doseq [el pulled]
      (f el))))

(defn replace-text
  [f]
  (fn [text-node]
    (let [text (.-data text-node)
          transformed (f text)]
      (set! (.-data text-node) transformed))))

(defn remove-vowels-from-dom
  []
  (walk-dom 
   text-node? 
   (replace-text remove-vowels)))
