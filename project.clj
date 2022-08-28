(defproject redolist "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.456"]

                 ;; Frontend
                 [cljsjs/react "18.2.0-0"]
                 [cljsjs/react-with-addons "15.6.1-0"]
                 [cljsjs/react-dom "18.2.0-0"]
                 [cljsjs/react-dom-server "18.2.0-0"]
                 [reagent "1.1.1" :exclusions [cljsjs/react cljsjs/react-dom cljsjs/react-dom-server]]
                 [re-frame "1.3.0"]
                 [secretary "1.2.3"]
                 [cljs-ajax "0.8.4"]
                 [garden "1.3.10"]

                 ;; Backend
                 [com.stuartsierra/component "1.1.0"]
                 [org.danielsz/system "0.4.7"]
                 [compojure "1.7.0"]
                 [ring/ring-defaults "0.3.3"]
                 [ring-middleware-format "0.7.5"]
                 [ring "1.9.5"]
                 [spicerack "0.1.6"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-garden "0.2.8"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "resources/public/css"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet   redolist.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.6"]

                   ;; For cider-jack-in-clojurescript
                   [figwheel-sidecar "0.5.20"]
                   [com.cemerick/piggieback "0.2.2"]

                   ;; dependencies for the reloaded workflow
                   [lambdaisland/garden-watcher "1.0.45"]
                   [reloaded.repl "0.2.4"]
                   [ns-tracker "0.4.0"]]

    :plugins      [[lein-figwheel "0.5.9"]]}
   :garden
   {:dependencies [[cljsjs/react "18.2.0-0"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "redolist.core/mount-root"}
     :compiler     {:main                 redolist.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            redolist.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})
