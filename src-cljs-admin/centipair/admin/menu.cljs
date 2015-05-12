(ns centipair.admin.menu
  (:require [reagent.core :as reagent]
            [centipair.core.ui :as ui]
            [centipair.core.components.notifier :as notify]
            [secretary.core :as secretary :refer-macros [defroute]]
            [goog.events :as events]
            [goog.history.EventType :as EventType])
  (:import goog.History))




(def admin-menu (reagent/atom 
                 [{:label "Dashboard" :url "/dashboard" :id "dashboard" :active false}
                  {:label "My projects" :url "/projects" :id "projects" :active false }
                  {:label "Responses" :url "/responses" :id "responses" :active false}
                  {:label "Applications" :url "/applications" :id "applications" :active false}]))


(defn single-menu-component [each]
  [:li  {:key (str "admin-menu-list-" (:id each)) :class (if (:active each) "active" "")}
       [:a {:href (str "#" (:url each)) :key (str "admin-menu-link-" (:id each))}
        (:label each)]])


(defn parent-menu-component [each]
  [:li {:key (str "admin-menu-list-" (:id each)) :class (if (:active each) "active" "")}
   [:a {:href "javascript:;"
        :data-toggle "collapse"
        :data-target (str "#" (:id each) "-children")
        :key (str "admin-menu-link-" (:id each))}
    [:i {:class (str "fa fa-fw fa-" (:icon each))}] " "
    (:label each)
    [:i {:class "fa fa-fw fa-caret-down"}]]
   [:ul {:id (str (:id each) "-children")
         :class "collapse"
         :key (str (:id each) "-children")}
    (map single-menu-component (:children each))]])


(defn menu-item [each]
  (if (:children each)
    (parent-menu-component each)
    (single-menu-component each)))

(defn menu-component []
  [:ul {:class "nav navbar-nav side-nav" ;;old class: nav nav-sidebar 
        :key (str "admin-menu-container" )}
   (doall 
    (map menu-item @admin-menu))])

(defn deactivate [id item]
  (if (:children item)
        (assoc item :children (map (partial deactivate id) (:children item)))
        (if (= id (:id item))
          (do
            ;;setting active-page here
            (assoc item :active true))
          (assoc item :active false))))


(defn activate-side-menu-item [id & [site-id]]
  ;;(.log js/console site-id)
  (do (notify/notify 200)
      (reset! admin-menu (into [] (map (partial deactivate id) @admin-menu)))
      ))


(defn render-admin-menu []
  (ui/render menu-component "admin-menu"))


(defroute search "/search/:url" [url query-params]
  (js/console.log (str "User: " url))
  (js/console.log (pr-str query-params)))


(defroute dashboard "/dashboard" []
  (activate-side-menu-item "dashboard")
  (js/console.log "dashboard"))


(defroute projects "/projects" []
  (activate-side-menu-item "projects")
  (js/console.log "projects"))


(defroute responses "/responses" []
  (activate-side-menu-item "responses")
  (js/console.log "responses"))


(defroute applications "/applications" []
  (activate-side-menu-item "applications")
  (js/console.log "applications"))


  

;; Quick and dirty history configuration.
(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))

 

