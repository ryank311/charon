(ns charon.core
  (:gen-class :main true)
  (:use [ring.middleware.json :only (wrap-json-params)]
        [ring.middleware.multipart-params :only (wrap-multipart-params)]
        [ring.middleware.params :only (wrap-params)]
        [ring.middleware.file :only (wrap-file)]
        [ring.middleware.head :only (wrap-head)]
        [ring.middleware.file-info :only (wrap-file-info)]
        [ring.middleware.resource :only (wrap-resource)]
        [ring.middleware.nested-params :only (wrap-nested-params)]
        [ring.middleware.keyword-params :only (wrap-keyword-params)]
        [ring.middleware.reload :only (wrap-reload)]
        [ring.middleware.session :only (wrap-session)]
        [ring.middleware.session.cookie :only (cookie-store)]
        [ring.middleware.cookies :only (wrap-cookies)]
        [ring.middleware.content-type :only (wrap-content-type)])
  (:require [org.httpkit.server :as server]
            [lichen.core :as lichen]
            [caribou.config :as config]
            [caribou.db :as db]
            [caribou.model :as model]
            [caribou.logger :as log]
            [caribou.repl :as repl]
            [caribou.core :as caribou]
            [caribou.app.pages :as pages]
            [caribou.app.template :as template]
            [caribou.app.middleware :as middleware]
            [caribou.app.helpers :as helpers]
            [caribou.app.cljs :as cljs]
            [caribou.admin.routes :as admin-routes]
            [caribou.admin.core :as admin-core]
            [caribou.api.routes :as api-routes]
            [caribou.api.core :as api-core]
            [caribou.app.handler :as handler]
            [charon.boot :as boot]
            [charon.routes :as routes]
            [charon.helpers :as user-helpers]))

(declare handler)

(defn reload-pages
  []
  (concat 
   (pages/convert-pages-to-routes
    admin-routes/admin-routes
    'caribou.admin.controllers
    "/_admin"
    admin-core/admin-wrapper)

   (pages/convert-pages-to-routes
    api-routes/api-routes
    'caribou.api.controllers
    "/_api"
    api-core/api-wrapper)

   (routes/build-routes
    routes/routes
    (config/draw :controller :namespace))

   (pages/convert-pages-to-routes
    (routes/gather-pages)
    (config/draw :controller :namespace))))

(defn init
  []
  (let [config (boot/boot)]
    (caribou/with-caribou config
      (template/init)
      (repl/repl-init)
      (cljs/brepl-init)
      (def handler
        (-> (handler/handler reload-pages)
            (helpers/wrap-helpers user-helpers/additional-helpers)
            (wrap-reload)
            (wrap-file (config/draw :assets :dir))
            (wrap-resource (config/draw :app :public-dir))
            (wrap-file-info)
            (wrap-content-type)
            (wrap-head)
            (lichen/wrap-lichen (config/draw :assets :dir))
            (middleware/wrap-path-info)
            (middleware/wrap-xhr-request)
            (wrap-json-params)
            (wrap-multipart-params)
            (wrap-keyword-params)
            (wrap-nested-params)
            (wrap-params)
            (cljs/wrap-cljs)
            (handler/wrap-caribou config)
            (wrap-session {:store (cookie-store {:key "REPLACEMEWITHKEY"})})
            (wrap-cookies))))))

;; ^^^^^^^^^^
;; for heroku
;; vvvvvvvvvv
(defn -main [& [port]]
  (init)
  (let [port (Integer. (or port (System/getenv "PORT") 33333))]
    (server/run-server #'handler {:port port})
    (println (format "Caribou running on port %s" port))))
