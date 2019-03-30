(ns cljs-color-clock.core
  (:require
   [reagent.core :as reagent :refer [atom]]))

(def state (atom {:time {:hour nil :min nil :sec nil} :show-hex false}))

(defn log [& arg]
  (js/console.log arg.arr))

(defn get-hour [t]
  (.getHours t))

(defn get-minutes [t]
  (.getMinutes t))

(defn get-seconds [t]
  (.getSeconds t))

(defn in []
  (reset! state (assoc @state :show-hex true)))

(defn out []
  (reset! state (assoc @state :show-hex false)))

(defn add-zero [num]
  (if (< num 9)
    (str "0" num)
    num))

(defn to-hex [num]
  (.toString num 16))

(defn format-num [num]
  (def show-hex (get @state :show-hex))
  (if (true? show-hex)
    (to-hex num)
    (add-zero num)))

(defn clock []
  (def hour (get-in @state [:time :hour]))
  (def minutes (get-in @state [:time :min]))
  (def seconds (get-in @state [:time :sec]))
  [:span {:on-mouse-over in :on-mouse-out out}
    [:span  (format-num hour)]
    [:span ":"]
    [:span  (format-num minutes)]
    [:span ":"]
    [:span  (format-num seconds)]])

(defn update-state []
  ((reset! state (assoc-in @state [:time :hour] (get-hour (js/Date.))))
   (reset! state (assoc-in @state [:time :min] (get-minutes (js/Date.))))
   (reset! state (assoc-in @state [:time :sec] (get-seconds (js/Date.))))))

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
