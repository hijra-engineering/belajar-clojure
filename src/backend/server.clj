(ns backend.server
  (:require [org.httpkit.server :as kit]))

(defonce server (atom nil))

(defn handler [_]
  {:status 200
   :body "Hello from Clojure"})

(defn start-server [port]
  (reset! server (kit/run-server #'handler {:port port})))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(start-server 5000)

(comment
  (start-server 5001)
  (stop-server))
