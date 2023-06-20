(ns backend.db
  (:require [clojure.java.jdbc :as jdbc]
            [honey.sql :as hsql]))

(def db-spec
  {:dbtype "sqlite"
   :dbname "todo.db"})

(defn prepare-table! []
  (jdbc/db-do-commands db-spec
                       (jdbc/create-table-ddl :task
                                              [[:id :integer "PRIMARY KEY AUTOINCREMENT"]
                                               [:name :text]
                                               [:created :datetime "DEFAULT CURRENT_TIMESTAMP"]
                                               [:completed :datetime]]
                                              {:conditional? true})))

(defn all-tasks []
  (jdbc/query db-spec (hsql/format {:select :* :from :task
                                    :order-by [[:created :desc]]})))

(defn active-tasks []
  (jdbc/query db-spec (hsql/format {:select :* :from :task
                                    :where [:is :completed nil]
                                    :order-by [[:created :desc]]})))

(defn completed-tasks []
  (jdbc/query db-spec (hsql/format {:select :* :from :task
                                    :where [:is-not :completed nil]
                                    :order-by [[:completed :desc]]})))

(defn mark-complete! [id]
  (jdbc/db-do-commands db-spec (hsql/format {:update :task
                                             :set {:completed :current_timestamp}
                                             :where [:= :id id]}
                                            {:inline true}))
  (jdbc/query db-spec (hsql/format {:select :* :from :task
                                    :where [:= :id id]})))


(comment

  (jdbc/db-do-commands db-spec
                       (hsql/format {:update :task
                                     :set {:completed :current_timestamp}
                                     :where [:= :id 2]}
                                    {:inline true}))

  (jdbc/db-do-commands db-spec "CREATE TABLE task (id INTEGER, name TEXT)")

  (jdbc/create-table-ddl :task [[:id :integer]
                                [:name :text]])

  (jdbc/create-table-ddl :task
                         [[:id :integer "PRIMARY KEY AUTOINCREMENT"]
                          [:name :text]]
                         {:conditional? true})

  (jdbc/insert! db-spec :task {:name "buy milk"})

  (jdbc/insert! db-spec :task {:name "get donut"})

  (jdbc/insert! db-spec :task {:name "order pizza"})

  (jdbc/insert! db-spec :task {:name "enjoy ice-cream"})

  (jdbc/update! db-spec :task {:name "get croissant"} ["id = ?" 2])

  (jdbc/delete! db-spec :task ["id = ?" 3])

  (jdbc/query db-spec ["SELECT * FROM task WHERE id = ?" 2])

  (hsql/format {:select :* :from :task})

  (hsql/format {:select :* :from :task :where [:= :id 2]})

  (jdbc/query db-spec (hsql/format {:select :* :from :task :where [:= :id 2]}))

  ;; TODO: other JDBC command
  )