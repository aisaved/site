(ns centipair.app
  (:require [centipair.init :as init]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(init/init!)
