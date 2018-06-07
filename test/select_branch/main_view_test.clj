(ns select-branch.main-view-test
  (:require [clojure.test :refer :all])
  (:require [select-branch.main-view :refer [model]]
            [beicon.core :as rx])
  (:import (io.reactivex Observable))
  (:import (io.reactivex.observers BaseTestConsumer TestObserver)))

(def ^:private threeBranches
  {:branches '("aaa" "bbb" "main") :current 2})

(defn assert-values
  [^TestObserver testObserver & args]
  (.assertValues testObserver (into-array Object args)))

(defn test-observable
  [^Observable observable]
  (.test observable))

(deftest model-test
  (-> threeBranches
      rx/just
      model
      test-observable
      (assert-values ["aaa" "bbb" "main"])))
