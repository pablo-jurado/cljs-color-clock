(ns cljs-color-clock.core
  (:require
   [reagent.core :as reagent :refer [atom]]))

(def state (atom {:time {:hour 10 :min 5 :sec 05} :show-hex false}))

(defn log [& arg]
  (js/console.log arg.arr))

(defn hour [t]
  (.getHours t))

(defn minutes [t]
  (.getMinutes t))

(defn seconds [t]
  (.getSeconds t))

(defn update-state []
  ((reset! state (assoc-in @state [:time :hour] (hour (js/Date.))))
   (reset! state (assoc-in @state [:time :min] (minutes (js/Date.))))
   (reset! state (assoc-in @state [:time :sec] (seconds (js/Date.))))))

(defn in []
  (reset! state (assoc @state :show-hex true)))

(defn out []
  (reset! state (assoc @state :show-hex false)))

(defn clock []
  (def hex (get @state :show-hex))
  [:span {:on-mouse-over in :on-mouse-out out}
    [:div (str hex)]
    [:span  (get-in @state [:time :hour])]
    [:span ":"]
    [:span  (get-in @state [:time :min])]
    [:span ":"]
    [:span  (get-in @state [:time :sec])]])

(js/setInterval update-state 1000)

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
