(ns cljs-color-clock.core
  (:require
   [reagent.core :as reagent :refer [atom]]))

(defn log [& arg]
  (js/console.log arg.arr))

(defn hour [t]
  (.getHours t))

(defn minutes [t]
  (.getMinutes t))

(defn seconds [t]
  (.getSeconds t))

(defn get-time [t]
  (str (hour t) ":" (minutes t) ":" (seconds t)))

(def my-atom (atom (get-time (js/Date.))))

(js/setInterval #(reset! my-atom (get-time (js/Date.))) 1000)

(defn clock []
  [:div "Color Clock"
    [:p @my-atom]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [clock] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
