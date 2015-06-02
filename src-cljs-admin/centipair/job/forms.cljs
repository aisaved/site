(ns centipair.job.forms
  (:require [reagent.core :as reagent]
            [centipair.core.components.input :as input]
            [centipair.core.utilities.validators :as v]
            [centipair.core.ui :as ui]
            [centipair.core.utilities.ajax :as ajax]))






(def job-id (reagent/atom {:id "job-id"}))
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


(def job-how-to-apply (reagent/atom {:id "job-how-to-apply" :label "How to apply" :type "textarea" :validator v/required}))
(def job-who-can-apply (reagent/atom {:id "who-can-apply" :label "Who can apply" :type "select"
                                      :options [{:label "Verified Users" :value "premium"}
                                                {:label "All Users" :value "all"}
                                                ]}))
(defn validate-job-budget [field]
  (.log js/console "validating field")
  (if (v/has-value? (:value (:text field)))
    {:valid true}
    {:valid false :message "This field is required"}))

(def job-budget (reagent/atom {:id "job-budget-option" :label "Job budget" :type "select-text"
                               :text {:id "job-budget"}
                               :select {:id "job-budget-interval" :options [{:label "Hourly" :value "hourly"}
                                                                   {:label "Weekly" :value "weekly"}
                                                                   {:label "Monthly" :value "monthly"}
                                                                   {:label "Annually" :value "annually"}
                                                                   {:label "Fixed Price" :value "fixed"}]}
                               :validator validate-job-budget}))

(def job-apply-description (reagent/atom {:id "job-apply-description" :label "Expain how someone can apply to this job" :type "description"}))
(def job-company-name (reagent/atom {:id "job-company-name" :label "Company / Contact Name" :type "text" :validator v/required}))
(def job-company-name-description (reagent/atom {:id "job-company-name-description" :label "E.G: Scott Williams, TechnoType Inc" :type "description"}))
(def job-company-location (reagent/atom {:id "job-company-location" :label "Company / Contact Location" :type "text" :validator v/required}))



(defn job-source-url [url]
  (if (nil? (:value @job-id))
    "/api/private/job"
    (str "/api/private/job/" (:value @job-id))))

(defn save-job []
  []
  (ajax/form-post
   job-form-state
   "/api/private/job"
   [job-id
    job-form-state
    job-title
    job-type
    job-location
    job-description
    job-budget
    job-how-to-apply
    job-who-can-apply
    job-budget
    job-company-location
    job-company-name
    ]
   (fn [response]
     (.log js/console response)
     )))


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
    job-budget
    job-how-to-apply
    job-apply-description
    job-who-can-apply
    job-company-name
    job-company-name-description
    job-company-location]
   job-button))


(defn render-job-form []
  (ui/render job-form "content"))
