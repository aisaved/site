(ns centipair.admin.init
  (:require [centipair.admin.menu :as admin-menu])
  )


(defn init-admin [] 
  (admin-menu/render-admin-menu)
  )
