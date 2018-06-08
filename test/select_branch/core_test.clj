(ns select-branch.core-test
  (:require [clojure.test :refer :all]
            [select-branch.core :refer :all]))

(deftest parse-branches-test
  (is (=
        (parse-branches '("  aaa " "  bbb " "* master"))
        {:branches '("aaa" "bbb" "master") :current 2})))
