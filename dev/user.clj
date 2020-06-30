(ns user
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]))


(require 'spyscope.core)
(s/check-asserts true)

(st/instrument)
