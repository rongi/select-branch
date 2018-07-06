(ns select-branch.main-view
  (:require
    [select-branch.git :refer [select-branch]]
    [beicon.core :as rx])
  (:import
    (io.reactivex Observable)
    (com.googlecode.lanterna.gui2 ActionListBox Window)
    (io.reactivex.subjects Subject)))

(defn model
  [^Observable branches
   ^Observable branch-selected]
  {:branches        (->> branches
                         (rx/map :branches))
   :branch-selected (->> branch-selected)})

(defn render
  [view view-model ^Subject branch-selected]
  (rx/on-value
    (:branches view-model)
    (fn [branches]
      (doseq [branch branches]
        (.addItem (:list-box ^ActionListBox view) branch (reify Runnable (run [_] (.onNext branch-selected branch)))))))
  (rx/on-value
    (:branch-selected view-model)
    (fn [selected-branch]
      (let
        [git-response (select-branch selected-branch)
         _ (->> (:window ^Window view) .close)
         death-rattle (->> git-response (clojure.string/join "\n"))
         _ (reset! (:death-rattle view) death-rattle)
         ]))))
