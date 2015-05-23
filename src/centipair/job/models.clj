(ns centipair.job.models
  (:use korma.core
        korma.db
        centipair.core.db.connection
        centipair.core.contrib.mail
        centipair.core.contrib.time
        centipair.core.utilities.pagination)
  (:require [centipair.core.contrib.time :as time]
            [validateur.validation :refer :all]
            [centipair.core.contrib.validator :as validator]))

(defentity job)
(defentity job-editor)


(def job-validator
  (validation-set
   (presence-of :job-title :message "Please enter a job title")
   (presence-of :job-location :message "Please enter the job location")
   (presence-of :job-how-to-apply :message "Please fill this field")
   (presence-of :job-company-name :message "Please fill this field")
   (presence-of :job-company-location :message "Please fill this field")))


(defn validate-job [params]
  (let [validation-result (job-validator params)]
    (if (valid? validation-result)
      true
      [false {:validation-result {:errors validation-result}}])))


(defn job-exists? [job-id])

(defn get-all-jobs [])

(defn get-job [job-id])

(defn create-job [params]
  (insert job (values 
               {:job_title (:job-title params)
                :job_type (:job-type params)
                :job_location (:job-location params)
                :job_description (:job-description params)
                :job_how_to_apply (:job-how-to-apply params)
                :job_company_name (:job-company-name params)
                :job_company_location (:job-company-location params)
                :job_budget (:job-budget params)
                :job_budget_interval (:job-budget-interval params)
                :job_created_date (time/sql-time-now)
                :job_updated_date (time/sql-time-now)
                :job_expiry_date (time/set-time-expiry 183) ;;six months
                })))


(defn update-job [params]
  (update job (set-fields
               {:job_title (:job-title params)
                :job_type (:job-type params)
                :job_location (:job-location params)
                :job_description (:job-description params)
                :job_how_to_apply (:job-how-to-apply params)
                :job_company_name (:job-company-name params)
                :job_company_location (:job-company-location params)
                :job_updated_date (time/sql-time-now)
                })
          (where {:job_id (params :job-id)})))


(defn publish-job [job-id]
  (update job (set-fields
               {:job_active true
                :job_published_date (time/sql-time-now)
                })
          (where {:job_id job-id})))


(defn delete-job [job-id]
  (delete job (where {:job_id job-id})))


(defn save-job [params]
  (if (validator/has-value? (:job-id params))
    (update-job params)
    (create-job params)))
