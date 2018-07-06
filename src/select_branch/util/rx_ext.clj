(ns select-branch.util.rx-ext
  (:require [beicon.core :as rx])
  (:import (io.reactivex Observable)))

(defn fn->obs
  [fn]
  (Observable/defer #(rx/just (fn))))
