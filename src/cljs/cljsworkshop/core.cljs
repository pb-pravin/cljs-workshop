(ns cljsworkshop.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require [goog.events :as events]
            [goog.dom :as dom]
            [secretary.core :as secretary])
  (:import goog.History))


(def app (dom/getElement "app"))

(defn set-html! [el content]
  (set! (.-innerHTML el) content))

(defroute home-path "/" []
  (set-html! app "<h1>Hello World from home page.</h1>"))

(defroute "/test" []
  (let [message (str "<h1>You navigated to test page!</h1>")]
    (set-html! app message)))

(defroute some-path "/:param" [param]
  (let [message (str "<h1>Parameters in url: <small>" param "</small>!</h1>")]
    (set-html! app message)))



(defroute "*" []
  (set-html! app "<h1>Not Found</h1>"))

(defn main
  []

  ;; Set secretary config for use the hashbang prefix
  (secretary/set-config! :prefix "#")

  ;; Attach event listener to history instance.
  (let [history (History.)
        gottotest (dom/getElement "gottotest")
        gottohome (dom/getElement "gottohome")]

    (events/listen history "navigate"
                   (fn [event]
                     (secretary/dispatch! (.-token event))))

    ;; Assign event listener
    (events/listen gottotest "click"
                   (fn [event]
                     ;; navigate to test
                     (secretary/dispatch! "/test")))

     ;; Assign event listener
    (events/listen gottohome "click"
                   (fn [event]
                     ;; navigate to home
                     (secretary/dispatch! "/")))

    (.setEnabled history true)))

(main)


