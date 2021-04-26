(defproject rgstr "0.1.0-SNAPSHOT"
  :description "Application registration service"
  :url "http://github.com/zelr0x/rgstr"
  :min-lein-version "2.0.0"
  :jvm-opts ["-server"]

  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :test-paths ["test/clj" "test/cljs"]
  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :app :compiler :output-dir]
                                    [:cljsbuild :builds :app :compiler :output-to]]

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.844"]
                 [compojure "1.6.1"]
                 [ring "1.9.2"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.1"]
                 [metosin/ring-http-response "0.9.2"]
                 [reagent "1.0.0"]
                 [re-frame "1.2.0"]
                 [cljs-ajax "0.8.3"]
                 [com.datomic/datomic-free "0.9.5697"]]

  :plugins [[lein-ring "0.12.5"]
            [lein-cljsbuild "1.1.8"]]

  :main rgstr.core
  :ring {:handler rgstr.handler/app :port 8080}
  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler
                             {:output-to "resources/public/js/rgstr.js"
                              :output-dir "resources/public/js/out"
                              :asset-path "js/out"
                              :main "rgstr.core"
                              :pretty-print true}}}}
  :figwheel {:css-dirs ["resources/public/css"]}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [day8.re-frame/re-frame-10x "1.0.2"]
                        [cheshire "5.10.0"]]
         :plugins [[lein-figwheel "0.5.20"]]
         :cljsbuild {:builds
                     {:app
                      {:figwheel {:on-jsload "rgstr.core/clear-cache-and-render!"}
                       :compiler {:closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}
                                  :preloads [day8.re-frame-10x.preload]}}}}}

   :uberjar {:omit-source true
             :uberjar-name "testapp.jar" ;; jar name is a task requirement
             :prep-tasks ["clean" "compile" ["cljsbuild" "once"]]
             :aot :all
             :hooks [leiningen.cljsbuild]
             :cljsbuild {:app {:jar true
                               :builds
                               [{:source-paths ["src/cljs"]
                                 :compiler
                                 {:optimizations :advanced
                                  :pretty-print false
                                  :closure-warnings {:externs-validation :off
                                                     :non-standard-jsdoc :off}}}]}}}})
