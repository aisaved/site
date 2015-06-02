(ns centipair.job.list
  (:require
   [reagent.core :as reagent]
   [centipair.core.utilities.ajax :as ajax]
   [centipair.core.ui :as ui]
   [centipair.core.components.table :refer [data-table
                                            generate-table-rows
                                            per-page]]))


(defn job-list-headers
  []
  [:tr
   [:th "Title"]
   [:th "Expires"]
   [:th "Action"]])

(def job-data (reagent/atom {:page 0
                              :id "job-table"
                              :url "/user"
                              :total 0
                              :rows [:tr [:td "Loading"]]
                              :headers (job-list-headers)
                              :create {:entity "job"} 
                              :delete {:action (fn [] (.log js/console "delete"))}
                              :site-settings-id nil
                              :id-field "job_id"}))

(defn delete-job [job-id]
  
  )

(defn job-row
  [row-data]
  [:tr {:key (str "table-row-" ((keyword (:id-field @job-data)) row-data))}
   [:td {:key (str "table-column-1-" ((keyword (:id-field @job-data)) row-data))} (:job_title row-data)]
   [:td {:key (str "table-column-2-" ((keyword (:id-field @job-data)) row-data))} (str (:job_expiry_date row-data))]
   [:td {:key (str "table-column-3-" ((keyword (:id-field @job-data)) row-data))}
    [:a {:href (str "#/job/edit/" (:job_id row-data))
         :key (str "row-edit-link-" ((keyword (:id-field @job-data)) row-data))} "Edit "]
    [:a {:href "javascript:void(0)" :on-click (partial delete-job (:job_account_id row-data))
        :key (str "row-delete-link-" ((keyword (:id-field @job-data)) row-data)) } " Delete"]]])

(defn create-job-data-list []
  (data-table job-data))


(defn render-job-list []
  (ui/render create-job-data-list "content"))


(defn fetch-job-list []
  (ajax/get-json
   "/api/private/jobs"
   {:page (:page @job-data)
   :per per-page}
   (fn [response]
     (generate-table-rows response job-data job-row))))
