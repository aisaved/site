(ns centipair.job.models
  (:use korma.core
        korma.db
        centipair.core.db.connection
        centipair.core.contrib.mail
        centipair.core.contrib.time
        centipair.core.utilities.pagination)
  (:require [centipair.core.contrib.time :as time])
  )

(defentity job)

(defn validate-job [params])

(defn create-job [params]
  (insert job (values 
               {:job_title (:job-title params)
                :job_type (:job-type params)
                :job_location (:job-location params)
                :job_description (:job-description params)
                :job_how_to_apply (:job-how-to-apply params)
                :job_company_name (:job-company-name params)
                :job_company_location (:job-company-location params)
                :job_created_date (time/sql-time-now)
                :job_updated_date (time/sql-time-now)
                :job_expiry_date (time/set-time-expiry 183)
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
          (where {:job_id (:params job-id)})))


(defn publish-job []
  (update job (set-fields
               {:job_active true
                :job_published_date (time/sql-time-now)
                })))


(defn delete-job)


(defn save-job [])
