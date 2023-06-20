(ns backend.server
  (:require [org.httpkit.server :as kit]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response]]
            [compojure.core :refer [defroutes GET POST]]
            [backend.db :as db]))

(defn hello-world [_]
  "Hello world with Compojure")

(defn make-greeter [name]
  (fn [_] (str "Hi " name)))

(defonce answer (atom 42))

(defn complete-task [id]
  (fn [_] (db/mark-complete! id)))

(defroutes handler
  (GET "/" [] hello-world)
  (GET "/api/v1" [] (db/all-tasks))
  (GET "/api/v1/active" [] (db/active-tasks))
  (GET "/api/v1/completed" [] (db/completed-tasks))
  (POST "/api/v1/complete/:id" [id] (complete-task id))
  (GET "/answer" [] (str "Answer is " @answer))
  (GET "/hi/:name" [name] (make-greeter name)))

(def app (wrap-json-response handler))

(defonce server (atom nil))

(defn start-server [port]
  (db/prepare-table!)
  (println (str "Starting server on port " port))
  (reset! server (kit/run-server (wrap-reload #'app)  {:port port})))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(when (nil? @server)
  (start-server 5000))

(comment
  (reset! answer 88)

  (start-server 5000)
  (stop-server)

  (db/all-tasks)

  ;; space for moar playground

  )
