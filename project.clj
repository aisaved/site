(defproject centipair "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [selmer "0.8.2"]
                 [ring-server "0.4.0"]
                 [com.taoensso/timbre "3.4.0"]
                 [com.taoensso/tower "3.0.2"]
                 [markdown-clj "0.9.66"]
                 [environ "1.0.0"]
                 [im.chit/cronj "1.4.3"]
                 [compojure "1.3.3"]
                 [ring/ring-defaults "0.1.4"]
                 [ring/ring-session-timeout "0.1.0"]
                 [ring-middleware-format "0.5.0"]
                 [bouncer "0.3.2"]
                 [prone "0.8.1"]
                 [org.clojure/tools.nrepl "0.2.10"]
                 [com.draines/postal "1.11.3"]
                 [korma "0.4.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [buddy "0.5.2"]
                 [lib-noir "0.9.9"]
                 [danlentz/clj-uuid "0.1.5"]
                 [com.novemberain/validateur "2.4.2"]
                 [liberator "0.12.2"]
                 [org.clojure/clojurescript "0.0-3211" :scope "provided"]
                 [org.clojure/tools.reader "0.9.2"]
                 [reagent "0.5.0"]
                 [cljsjs/react "0.13.1-0"]
                 [reagent-forms "0.5.0"]
                 [reagent-utils "0.1.4"]
                 [secretary "1.2.3"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-ajax "0.3.11"]
                 [com.lucasbradstreet/cljs-uuid-utils "1.0.1"]
                 [org.immutant/web "2.0.0"]]

  :min-lein-version "2.0.0"
  :uberjar-name "centipair.jar"
  :jvm-opts ["-server"]

;;enable to start the nREPL server when the application launches
;:env {:repl-port 7001}

  :main centipair.main

  :plugins [
            [lein-environ "1.0.0"]
            [lein-ancient "0.6.5"]
            [lein-cljsbuild "1.0.5"]]
    
  
  :clean-targets ^{:protect false} ["resources/public/cljs"
                                    "resources/public/cljs-admin"]
  
  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src-cljs"]
     :compiler
     {:output-dir "resources/public/cljs/out"
      :externs ["react/externs/react.js"]
      :optimizations :none
      :output-to "resources/public/cljs/app.js"
      :pretty-print true}}
    :app-admin
    {:source-paths ["src-cljs-admin"]
     :compiler
     {:output-dir "resources/public/cljs-admin/out"
      :externs ["react/externs/react.js"]
      :optimizations :none
      :output-to "resources/public/cljs-admin/app-admin.js"
      :pretty-print true}}
    }}
  
  :immutant {:war 
           {:context-path "/"}}
  
  :profiles
  {:uberjar {:omit-source true
             :env {:production true}
              :hooks [leiningen.cljsbuild]
              :cljsbuild
              {:jar true
               :builds
               {:app
                {:source-paths ["env/prod/cljs"]
                 :compiler {:optimizations :advanced :pretty-print false}}
                :app-admin
                {:source-paths ["env/prod/cljs-admin"]
                 :compiler {:optimizations :advanced :pretty-print false}}}
               
               } 
             
             :aot :all}
   :dev {:dependencies [[ring-mock "0.1.5"]
                        [ring/ring-devel "1.3.2"]
                        [pjstadig/humane-test-output "0.7.0"]
                        [weasel "0.6.0"]
                        [org.clojure/tools.nrepl "0.2.10"]
                        [com.cemerick/piggieback "0.2.1"]]
         :source-paths ["env/dev/clj"]
         :plugins [[lein-figwheel "0.3.1"]]
          :cljsbuild
          {:builds
           {:app
            {:source-paths ["env/dev/cljs"] :compiler {:source-map true}}
            :app-admin
            {:source-paths ["env/dev/cljs-admin"] :compiler {:source-map true}}
            }} 
         
         :figwheel{:http-server-root "public"
          :server-port 3449
          :css-dirs ["resources/public/css"]
          :ring-handler centipair.handler/app}
         
         :repl-options {:init-ns centipair.main}
         :injections [(require 'pjstadig.humane-test-output)
                      (pjstadig.humane-test-output/activate!)]
         :env {:dev true}}})
