(ns example.hello-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [example.hello :as hello]))

(deftest handle-event
  (let [expected (slurp (io/resource "example/greeting.json"))]
    (is (= expected (hello/greeting "tests")))))
