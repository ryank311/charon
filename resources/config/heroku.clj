{:logging {:loggers [{:type :stdout :level :warn}]}
 :database {:classname    "org.postgresql.Driver"
            :subprotocol  "postgresql"
            :database     "d31n7kg999rdis"
            :host         "ec2-54-235-127-33.compute-1.amazonaws.com"
            :port         5432
            :user         "mqcaqtpznmzbjz"
            :password     "G_nTNzchIxV58o2N5PchaEeaok"
            :ssl          true
            :sslfactory   "org.postgresql.ssl.NonValidatingFactory"}
 :controller {:namespace "charon.controllers" :reload :never}
 :cache-templates :always}
