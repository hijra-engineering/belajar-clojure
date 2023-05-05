(ns backend.server
  (:require [org.httpkit.server :as kit]
            [compojure.core :refer [defroutes GET]]))

(defn hello-world [_]
  "Hallo world with Compojure")

(defn make-greeter [name]
  (fn [_] (str "Hi " name)))

(defroutes handler
  (GET "/" [] hello-world)
  (GET "/hi" [] "Selamat pagi!")
  (GET "/hi/:name" [name] (make-greeter name)))

(defonce server (atom nil))

(defn start-server [port]
  (reset! server (kit/run-server #'handler {:port port})))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(start-server 5000)

(comment
  (start-server 5001)
  (stop-server)

  ;; simplest handler
  (defroutes handler
    (GET "/" [] "Hallo world with Compojure"))

  ;; multiple paths
  (defroutes handler
    (GET "/" [] "Hallo world with Compojure")
    (GET "/hi" [] "Hi there!"))

  ;; use functions, anonymous and declared
  (defn hi-there [_]
    "Hi there!")
  (defroutes handler
    (GET "/" [] (fn [_] "Hello world"))
    (GET "/hi" [] hi-there))

  ;; higher-order
  (defn make-salam []
    (fn [_] "Salam"))
  (defroutes handler
    (GET "/" [] hello-world)
    (GET "/hi" [] (make-salam)))

  ;; URL parameter
  (defn make-greeter [name]
    (fn [_] (str "Hi " name)))
  (defroutes handler
    (GET "/" [] hello-world)
    (GET "/hi/:name" [name] (make-greeter name))))