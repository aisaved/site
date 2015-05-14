(ns centipair.job.forms
  (:require [reagent.core :as reagent]
            [centipair.core.components.input :as input]
            [centipair.core.utilities.validators :as v]
            [centipair.core.ui :as ui]))




(def job-form-state (reagent/atom {:id "job-form" :type "form" :title "Post a job"}))
(def job-title-description (reagent/atom {:id "job-title-description" :type "description" :label "E.G: Software Engineer at Company"}))
(def job-title (reagent/atom {:id "job-title" :label "Job Title" :type "text" :validator v/required}))
(def job-type (reagent/atom {:id "job-type" :label "Type" :type "select"
                             :options [{:label "Full time" :value "full-time"}
                                       {:label "Part time" :value "part-time"}
                                       {:label "Contract" :value "contract"}]}))


(def job-location (reagent/atom {:id "job-location" :label "Location" :type "text" :validator v/required}))
(def job-location-description (reagent/atom {:id "job-location-description" :label "E.G: San Francisco, London, Anywhere" :type "description"}))

(def job-description (reagent/atom {:id "job-description" :label "Description" :type "markdown" :description "This editor supports markdown. Click privew to view your post"}))

(def job-company-name (reagent/atom {:id "job-company-name" :label "Company Name"}))

(def how-to-apply (reagent/atom {:id "how-to-apply" :label "How to apply" :type "textarea"}))
(def company-name (reagent/atom {:id "company-name" :label "Company name" :type "text"}))

(defn save-job []
  
  )


(def job-button (reagent/atom {:type "button-group"
                               :buttons [{:id "job-button-save" :label "Save" :on-click save-job}
                                         {:id "job-button-publish" :label "Save & Publish" :on-click save-job}]}))


(defn job-form []
  (input/form-aligned  
   job-form-state
   [job-title
    job-title-description
    job-type
    job-location
    job-location-description
    job-description
    how-to-apply
    company-name]
   job-button))


(defn render-job-form []
  (ui/render job-form "content"))
