(ns backend.server
  (:require [org.httpkit.server :as kit]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]))

(defn hello-world [_]
  "Hello world with Compojure")

(defn make-greeter [name]
  (fn [_] (str "Hi " name)))

(defroutes handler
  (GET "/" [] hello-world)
  (GET "/hi/:name" [name] (make-greeter name)))

(defonce server (atom nil))

(defn start-server [port]
  (println (str "Starting server on port " port))
  (reset! server (kit/run-server (wrap-reload #'handler) {:port port})))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(when (nil? @server)
  (start-server 5000))

(comment
  (start-server 5000)
  (stop-server)

  ;; when this replacement is used and evaluated,
  ;; wrap-reload takes care of applying the new routes

  (defroutes handler
    (GET "/" [] hello-world)
    (GET "/salam" [] "Salam")
    (GET "/hi/:name" [name] (make-greeter name))))
