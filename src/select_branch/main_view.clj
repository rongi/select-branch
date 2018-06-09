(ns select-branch.main-view
  (:require [beicon.core :as rx])
  (:import (io.reactivex Observable)
           (com.googlecode.lanterna.gui2 ActionListBox)))

(defn model
  [^Observable branches]
  (->> branches
       (rx/map :branches)))

(defn render
  [view view-model]
  (rx/on-value view-model
               (fn [branches]
                 (doseq [branch branches]
                   (.addItem (:list-box view) branch (reify Runnable (run [_])))))))
