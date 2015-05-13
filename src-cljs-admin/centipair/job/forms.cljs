(ns centipair.job.forms
  (:require [reagent.core :as reagent]
            [centipair.core.components.input :as input]
            [centipair.core.utilities.validators :as v]
            [centipair.core.ui :as ui]))




(def job-form-state (reagent/atom {:id "job-form" :type "form"}))

(def job-title (reagent/atom {:id "job-title" :label "Job Title" :type "text" :validator v/required}))

(def job-description (reagent/atom {:id "job-description" :label "Job Description" :type "textarea"}))



(defn save-job []
  
  )


(def job-button (reagent/atom {:id "job-button" :label "Save" :on-click save-job}))


(defn job-form []
  (input/form-aligned  
   job-form-state
   [job-title job-description]
   job-button))


(defn render-job-form []
  (ui/render job-form "content"))
