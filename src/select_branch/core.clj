(ns select-branch.core
  (:gen-class :main true)
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as sh]
            [clojure.string :as str]
            [select-branch.util :refer [first-index]]
            [select-branch.main-view :as main-view]
            [select-branch.rx-ext :refer [fn->obs]]
            [select-branch.ui :as ui])
  (:import (io.reactivex Observable)))

(defn- get-branches-strings []
  (let [cmd ["git" "branch"]
        proc (.exec (Runtime/getRuntime) (into-array cmd))]
    (with-open [rdr (io/reader (.getInputStream proc))]
      (doall (line-seq rdr)))))

(defn parse-branches [branches-strings]
  (do
    (let [branches (->> branches-strings
                        (map #(str/replace % "*" ""))
                        (map str/trim))
          current-branch-index (->> branches-strings
                                    (first-index #(.contains % "*")))]
      {:branches branches :current current-branch-index})
    ))

(defn- get-branches []
  ^Observable
  (fn->obs #(parse-branches (get-branches-strings))))

(defn start-view
  [view]
  (let
    [view-model (main-view/model (get-branches))]
    (main-view/render view view-model)))

(defn -main [& args]
  (ui/start-gui start-view))

;(if-not (empty? args)
;  ; Foreach arg, print the arg...
;  (doseq [arg args] (println arg))
;
;  ; Handle failure however here
;  (throw (Exception. "Must have at least one argument!")))

;https://yobriefca.se/blog/2014/03/02/building-command-line-apps-with-clojure/
