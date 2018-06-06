(ns select-branch.main-view
  (:require [beicon.core :as rx])
  (:import (io.reactivex Observable)))

(defn model
  [^Observable branches]
  (->> branches
       (rx/map :branches)))