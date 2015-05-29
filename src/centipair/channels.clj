(ns centipair.channels
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))


(def mail-channel (chan))


(defn process-email
  [params]
  (Thread/sleep (rand 5000))
  (println params))

(defn init-mail-channel
  []
  (go 
    (while true
      (process-email (<! mail-channel)))))


(defn send-async-mail
  [params]
  (go (>! mail-channel params)))
