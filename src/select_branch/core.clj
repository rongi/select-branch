(ns select-branch.core
  (:gen-class :main true)
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as sh]
            [clojure.string :as str]
            [select-branch.util :refer [first-index]]
            [select-branch.main-view :as main-view]
            [select-branch.rx-ext :as rx-ext]
            [select-branch.ui :as ui])
  (:import (io.reactivex Observable)))

(defn- get-branches-string []
  (let [cmd ["git" "branch"]
        proc (.exec (Runtime/getRuntime) (into-array cmd))]
    (with-open [rdr (io/reader (.getInputStream proc))]
      (doall (line-seq rdr)))))

(defn parse-branches [branches-string]
  (do
    (let [branches (->> branches-string
                        (map #(str/trim %))
                        (map #(str/replace % "*" "")))
          current-branch-index (->> branches-string
                                    (first-index #(.contains % "*")))]
      {:branches branches :current current-branch-index})
    ))

(defn- get-branches []
  ^Observable
  (rx-ext/defer (parse-branches get-branches-string)))

(defn -main [& args]
  (let
    [view (ui/create-lanterna-view)
     view-model (main-view/model get-branches)
     ]
    (main-view/render view view-model)))

;(if-not (empty? args)
;  ; Foreach arg, print the arg...
;  (doseq [arg args] (println arg))
;
;  ; Handle failure however here
;  (throw (Exception. "Must have at least one argument!")))

;https://yobriefca.se/blog/2014/03/02/building-command-line-apps-with-clojure/
