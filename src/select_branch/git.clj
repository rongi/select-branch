(ns select-branch.git
  (:require [clojure.java.io :as io]))

(defn read-branches []
  (let [cmd ["git" "branch"]
        process (.exec (Runtime/getRuntime) (into-array cmd))]
    (with-open [rdr (io/reader (.getInputStream process))]
      (doall (line-seq rdr)))))

(defn select-branch [branch]
  (let [cmd ["git" "checkout" branch]
        process (.exec (Runtime/getRuntime) (into-array cmd))
        input-stream (with-open [rdr (io/reader (.getInputStream process))]
                       (doall (line-seq rdr)))
        err-stream (with-open [rdr (io/reader (.getErrorStream process))]
                     (doall (line-seq rdr)))]
    (concat input-stream err-stream)))
