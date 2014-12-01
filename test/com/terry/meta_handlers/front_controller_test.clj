(ns com.terry.meta-handlers.front-controller-test
  (:use clojure.test)
  (:require [com.terry.meta-handlers.front-controller :as fc]
            [com.terry.meta-handlers.handlers1 :as h1]
            [com.terry.meta-handlers.handlers2 :as h2]))

(deftest test-handle []
  (let [namespaces ['com.terry.meta-handlers.handlers1 'com.terry.meta-handlers.handlers2]]
    (is (= "test page"  (:body (fc/handle {:uri "/test.html" :request-method :get} namespaces))))))

(deftest test-perf []
  (let [req {:uri "/perf-test-dhdfh.html" :request-method :get}
        nses ['com.terry.meta-handlers.handlers1 'com.terry.meta-handlers.handlers2]]
    (time (doall (for [i (range 1000)] (fc/handle req nses :cache? true))))
    0))

(deftest test-perf2 []
  (let [namespaces ['com.terry.meta-handlers.perf-test-handlers]]
    (println "Time for 1000 matches")
       (time
           (dotimes [_ 1000]
             (fc/handle {:uri "/e.html" :request-method :get} namespaces)))))
