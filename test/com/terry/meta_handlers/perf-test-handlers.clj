(ns com.terry.meta-handlers.perf-test-handlers)

(defn ^{:get "/index.html"} ptest-page [request]
  {:body "test page" :status 200})

(defn ^{:get "/a.html"} ptest-page2 [request]
  {:body "test page 2" :status 200})

(defn ^{:get "/b.html"} ptest-page3 [request]
  {:body "test page 3" :status 200})

(defn ^{:get #"/c.html"} ptest-page [request]
  {:body "test page 4" :status 200})

(defn ^{:get #"/d.html"} ptest-page2 [request]
  {:body "test page 5" :status 200})

(defn ^{:get #"e.html"} ptest-page3 [request]
  {:body "test page 6" :status 200})
