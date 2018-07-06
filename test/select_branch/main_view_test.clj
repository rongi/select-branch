(ns select-branch.main-view-test
  (:require [clojure.test :refer :all])
  (:require [select-branch.main-view :refer [model]]
            [beicon.core :as rx])
  (:import (io.reactivex Observable))
  (:import (io.reactivex.observers BaseTestConsumer TestObserver)))

(def ^:private threeBranches
  {:branches '("aaa" "bbb" "main") :current 2})

(defn ^:private create-model
  ([]
   (create-model (rx/empty)))
  ([^Observable branch-selected]
   (model
     (rx/just threeBranches)
     branch-selected)))

(defn assert-values
  [^TestObserver testObserver & args]
  (.assertValues testObserver (into-array Object args)))

(defn test-observable
  [^Observable observable]
  (.test observable))

(deftest model-test
  (testing "populates view"
    (-> (create-model)
        :branches
        test-observable
        (assert-values ["aaa" "bbb" "main"])))
  (testing "emits item-selected on item selected"
    (-> (create-model (rx/just "main"))
        :branch-selected
        test-observable
        (assert-values "main"))))
