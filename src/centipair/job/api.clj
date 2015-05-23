(ns centipair.job.api
  (:use compojure.core)
  (:require [liberator.core :refer [resource defresource]]
            [centipair.core.contrib.response :as response]
            [centipair.job.models :as job-models]
            [centipair.core.auth.user.models :as user-models]
            [centipair.core.contrib.cookies :as cookies]))



(defn job-editor? [context]
  (let [job-id (:params (:request context))
        auth-token (cookies/get-auth-token)
        user-account (user-models/get-user-session auth-token)]
    
    ))


(defn api-job [& [source]]
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :handle-ok (fn [context] (if (nil? source) 
                             (job-models/get-all-jobs (:params (:request context))) 
                             (job-models/get-job source))))


(defn api-job-crud [source]
  :available-media-types ["application/json"]
  :allowed-methods [:post :delete :put]
  :processable? (fn [context]
                  
                  (if (= (:request-method (:request context)) :delete)
                    true
                    (job-models/validate-job (:params (:request context)))))
  :exists? (fn [context] (if (nil? source) true (job-models/job-exists?  source)))
  :handle-unprocessable-entity (fn [context] (:validation-result context))
  :post! (fn [context]
           {:created (job-models/save-job (:params (:request context)))})
  :handle-created (fn [context] (:created context))
  :delete! (fn [context]  (job-models/delete-job source))
  :delete-enacted? false)

(defresource admi-api-user [&[source]]
  :available-media-types ["application/json"]
  :allowed-methods [:post :get :delete :put]
  :processable? (fn [context] (if (= (:request-method (:request context)) :get)
                                true
                                (if (= (:request-method (:request context)) :delete)
                                  true
                                  (job-models/validate-job (:params (:request context))))))
  :exists? (fn [context] (if (nil? source) true (job-models/job-exists?  source)))
  :handle-unprocessable-entity (fn [context] (:validation-result context))
  :post! (fn [context]
           {:created (job-models/save-job (:params (:request context)))})
  :handle-created (fn [context] (:created context))
  :delete! (fn [context]  (job-models/delete-job source))
  :delete-enacted? false
  :handle-ok (fn [context] (if (nil? source) 
                             (job-models/get-all-jobs (:params (:request context))) 
                             (job-models/get-job source))))


(defroutes api-job-routes
  (GET "/private/api/jobs" [] (api-job))
  (GET "/private/api/job/:id" [id] (api-job id))
  (POST "/private/api/job" [] (api-job-crud))
  (DELETE "/private/api/job/:id" [id] (api-job-crud id)))
