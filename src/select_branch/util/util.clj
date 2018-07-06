(ns select-branch.util.util)

; Returns first index of a collection matching given predicate
(defn first-index [pred coll]
  (first (keep-indexed #(when (pred %2) %1) coll)))

