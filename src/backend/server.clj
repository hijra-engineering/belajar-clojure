(ns backend.server
  (:require [org.httpkit.server :as kit]))

(defn handler [_]
  {:status 200
   :body "Hello from Clojure"})

(kit/run-server handler {:port 5000})