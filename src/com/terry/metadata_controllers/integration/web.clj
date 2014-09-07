(ns com.terry.metadata-controllers.integration.web
  (:use compojure.core)
  (:use ring.adapter.jetty)
  (:use ring.middleware.reload)
  (:use [clojure.tools.logging :only (debug info error)])
  (:require [com.terry.metadata-controllers.integration.middleware :as middleware]
            [ring.middleware.params :as params]
            [ring.util.response     :as response]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [selmer.parser :refer (render-file)]))

(defn wrap-logging [handler]
  "wrap the request and add some logging"
  (fn [request]
    (info (str "Processing request " (:uri request)) )
    (handler request)))

(defn render-response [template data]
  "We need to put the html data inside a map in the format that ring middleware requires. The html body must be in the 'body' key."
  {:body (render-file template data)}
  )

(defn global-error-page [request exception]
  "Global error page handler"
  (render-response "templates/global-error.html" (conj request {:error exception} )))

(defn wrap-exception [handler]
  "wrap the requests in a try catch to show a custom error page"
  (fn [request]
    (info "wrapping exception")
    (try (handler request)
         (catch Exception e
           (error  (str "An exception occured whilst processing request: " (.getMessage e)))
           (error e)
           (conj (global-error-page request e) {:status 500})))))



(defn index-page [request]
  "Handles a request for the homepage"
  (render-response "templates/index.html" {}))

(defn ^{:get "/test.html"} test-page [request]
  (render-response "templates/test.html" request))

(defroutes main-routes
           "Define URL to handler mappings"
           (GET "/" request  (index-page request))
           (route/resources "/")
           (route/not-found "Page not found"))

;(handler/site main-routes)
(def app
  (-> handler
      #(middleware/wrap-front-controller 'com.terry.metadata-controllers.integration.web)
      ;(wrap-logging)
      ;(wrap-exception)
      ))

(defn init []
  (selmer.parser/cache-off!)
  )

(defn boot []
  (run-jetty #'app {:port 8080}))
