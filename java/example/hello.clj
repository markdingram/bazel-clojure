(ns example.hello
  (:require [cheshire.core :as json])
  (:gen-class))

(defn greeting [name]
  (json/generate-string {:message (str "Hello from " name "!")}))

(defn -main []
 (println (greeting "Clojure")))

;; try me from the repl
(comment
  (greeting "Roger Repl"))