(ns centipair.admin.init
  (:require [centipair.admin.menu :as admin-menu]
            [centipair.core.components.editor :as editor]
            )
  )


(defn init-admin [] 
  (admin-menu/render-admin-menu)
  (editor/init-markdown-channel))
