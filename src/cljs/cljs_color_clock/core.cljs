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

(defn in []
  (log "in"))

(defn out []
  (log "out"))

(defn clock []
  [:span {
    :on-mouse-over in
    :on-mouse-out out} @my-atom])

(defn app []
  [:div
    [:h4 "Color Clock"]
    [clock]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
