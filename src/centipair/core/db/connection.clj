(ns centipair.core.db.connection
  (:use korma.db))

(defdb db (postgres {:db "store"
                     :user "centipair"
                     :password "c3nt1p@1r"}))

