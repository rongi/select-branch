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
               (-> (:list-box view)
                   (.addItem "Item1" (reify Runnable (run [_])))
                   (.addItem "Item2" (reify Runnable (run [_]))))))
