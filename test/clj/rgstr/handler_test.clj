(ns rgstr.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [rgstr.handler :refer :all]
            [cheshire.core :as json]))

(def context-root "/testapp")

(deftest test-app
  (testing "General routes"
    (are [status path] (= status (:status (app (mock/request :get path))))
      200 context-root
      ))

  (testing "List applications endpoint returns a collection"
    (let [response (app (mock/request :get (str context-root "/applications")))]
      (is (= (:status response) 200))
      (is (seq? (json/parse-string (:body response))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
