(ns select-branch.git
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as sh]))

(defn read-branches []
  (let [cmd ["git" "branch"]
        proc (.exec (Runtime/getRuntime) (into-array cmd))]
    (with-open [rdr (io/reader (.getInputStream proc))]
      (doall (line-seq rdr)))))

(defn select-branch [branch]
  (let [cmd ["git" "checkout" branch]
        proc (.exec (Runtime/getRuntime) (into-array cmd))]
    (with-open [rdr (io/reader (.getInputStream proc))]
      (doall (line-seq rdr)))))
