(ns select-branch.core
  (:gen-class :main true)
  (:require
    [clojure.string :as str]
    [select-branch.util.util :refer [first-index]]
    [select-branch.git :refer [read-branches]]
    [select-branch.main-view :as main-view]
    [select-branch.util.rx-ext :refer [fn->obs]]
    [select-branch.ui :as ui])
  (:import
    (io.reactivex Observable)
    (io.reactivex.subjects PublishSubject)))

(defn parse-branches [branches-strings]
  (let [branches (->> branches-strings
                      (map #(str/replace % "*" ""))
                      (map str/trim))
        current-branch-index (->> branches-strings
                                  (first-index #(.contains % "*")))]
    {:branches branches :current current-branch-index}))

(defn- get-branches []
  ^Observable
  (fn->obs #(parse-branches (read-branches))))

(defn start-view
  [view]
  (let
    [branch-selected (PublishSubject/create)
     view-model (main-view/model (get-branches) branch-selected)]
    (main-view/render view view-model branch-selected)))

(defn -main [& args]
  (ui/start-gui start-view))

;(if-not (empty? args)
;  ; Foreach arg, print the arg...
;  (doseq [arg args] (println arg))
;
;  ; Handle failure however here
;  (throw (Exception. "Must have at least one argument!")))

;https://yobriefca.se/blog/2014/03/02/building-command-line-apps-with-clojure/
