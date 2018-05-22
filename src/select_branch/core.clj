(ns select-branch.core
  (:gen-class :main true)
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as sh]))

(defn get-branches []
  (let [cmd ["git" "branch"]
        proc (.exec (Runtime/getRuntime) (into-array cmd))]
    (with-open [rdr (io/reader (.getInputStream proc))]
      (doseq [line (line-seq rdr)]
        (println line)))))

(defn run-app [get-branches]
  (get-branches))

; & creates a list of var-args
(defn -main [& args]
  (run-app
    get-branches))

;(if-not (empty? args)
;  ; Foreach arg, print the arg...
;  (doseq [arg args] (println arg))
;
;  ; Handle failure however here
;  (throw (Exception. "Must have at least one argument!")))

;https://yobriefca.se/blog/2014/03/02/building-command-line-apps-with-clojure/
