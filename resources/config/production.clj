{:logging {:loggers [{:type :stdout :level :warn}
                     ;; {:type :remote :host "beast.local" :level :debug}
                     ;; {:type :file :file "caribou-logging.out" :level :warn}
                     ]}
 :database {:classname    "org.postgresql.Driver"
            :subprotocol  "postgresql"
            :host         "localhost"
            :database     "charon_production"
            :user         "postgres"
            :password     "postgres"}
 :controller {:namespace "charon.controllers" :reload :never}
 :cache-templates :always}
