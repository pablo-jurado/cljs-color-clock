(ns cljs-color-clock.core
  (:require
   [reagent.core :as reagent :refer [atom]]))

(def state (atom {:time {:hour 0 :min 0 :sec 0} :show-hex false}))

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

(defn to-string [num]
  (if (< num 9)
    (str "0" num)
    (str num)))

(defn to-hex [string]
  (js/parseInt string 16))

(defn format-num [num]
  (def show-hex (get @state :show-hex))
  (def string (to-string num))
  (if (true? show-hex)
    (to-hex string)
    string))

(defn clock []
  (def hour (get-in @state [:time :hour]))
  (def minutes (get-in @state [:time :min]))
  (def seconds (get-in @state [:time :sec]))
  (def color (str "#" (to-hex hour) (to-hex minutes) (to-hex seconds)))
  [:div.wrapper {:style {:background (str "radial-gradient(at top left, white , " color ")")}}
    [:div.clock {:on-mouse-over in :on-mouse-out out}
      [:div.time
        [:span  (format-num hour)]
        [:span ":"]
        [:span  (format-num minutes)]
        [:span ":"]
        [:span  (format-num seconds)]]]])

(defn update-state []
  ((reset! state (assoc-in @state [:time :hour] (get-hour (js/Date.))))
   (reset! state (assoc-in @state [:time :min] (get-minutes (js/Date.))))
   (reset! state (assoc-in @state [:time :sec] (get-seconds (js/Date.))))))

(js/setInterval update-state 1000)

(defn app []
  [:div
    [:h2 "Color Clock"]
    [clock]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
