(ns backend.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec
  {:dbtype "sqlite"
   :dbname "todo.db"})

(defn all-tasks []
  (jdbc/query db-spec "SELECT * FROM task"))

(comment

  (jdbc/db-do-commands db-spec "CREATE TABLE task (id INTEGER, name TEXT)")

  (jdbc/create-table-ddl :task [[:id :integer]
                                [:name :text]])

  (jdbc/create-table-ddl :task
                         [[:id :integer "PRIMARY KEY AUTOINCREMENT"]
                          [:name :text]]
                         {:conditional? true})

  (jdbc/db-do-commands db-spec
                       (jdbc/create-table-ddl :task
                                              [[:id :integer "PRIMARY KEY AUTOINCREMENT"]
                                               [:name :text]]
                                              {:conditional? true}))

  (jdbc/insert! db-spec :task {:name "buy milk"})

  (jdbc/insert! db-spec :task {:name "get donut"})

  (jdbc/update! db-spec :task {:name "get croissant"} ["id = ?" 2])

  (jdbc/delete! db-spec :task ["id = ?" 3])

  (jdbc/query db-spec ["SELECT * FROM task WHERE id = ?" 2])

;; TODO: other JDBC command
  )
