(ns redolist.core
    (:require [reagent.core :as reagent]
              [reagent.dom :as reagent-dom]
              [re-frame.core :as re-frame]
              [redolist.events]
              [redolist.subs]
              [redolist.views :as views]
              [redolist.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent-dom/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
