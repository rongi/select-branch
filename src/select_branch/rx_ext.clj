(ns select-branch.rx-ext
  (:import (io.reactivex Observable)))

(defn defer
  [supplier]
  (Observable/defer supplier))
