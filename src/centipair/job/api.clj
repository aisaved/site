(ns centipair.job.api
  (:use compojure.core)
  (:require [liberator.core :refer [resource defresource]]
            [centipair.core.contrib.response :as response]
            [centipair.job.models :as job-models]
            [centipair.core.contrib.cookies :as cookies]))



(defresource api-job-list [& [source]]
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :handle-ok (fn [context] (if (nil? source) 
                             (job-models/get-all-jobs (:params (:request context))) 
                             (job-models/get-job source))))


(defresource api-job-crud [&[source]]
  :available-media-types ["application/json"]
  :allowed-methods [:post :delete :put]
  :authorized? (fn [context]
                 (if (nil? source)
                   true
                   (job-models/job-editor? (:request context) source)))
  :processable? (fn [context]
                  (if (= (:request-method (:request context)) :delete)
                    true
                    (job-models/validate-job (:params (:request context)))))
  :exists? (fn [context]
             (if (nil? source) true (job-models/job-exists?  source)))
  :handle-unprocessable-entity (fn [context] (:validation-result context))
  :post! (fn [context]
           {:created (job-models/save-job (:params (:request context)))})
  :handle-created (fn [context] 
                    (:created context))
  :delete! (fn [context]  (job-models/delete-job source))
  :delete-enacted? false
  :handle-ok (fn [context] (if (nil? source) 
                             (job-models/get-all-jobs (:request context)) 
                             (job-models/get-job source))))


(defroutes api-job-routes
  (GET "/api/private/jobs" [] (api-job-list))
  (GET "/api/private/job/:id" [id] (api-job-list id))
  (POST "/api/private/job" [] (api-job-crud))
  (DELETE "/api/private/job/:id" [id] (api-job-crud id)))
