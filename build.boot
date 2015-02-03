(set-env!
 :project 'com.paren/isomorphiclj
 :version "0.0.1-SNAPSHOT"
 :description "Utilities for writing platform-independent code."
 :url "https://github.com/parenthesis/isomorphiclj"
 :scm {:url "https://github.com/parenthesis/isomorphiclj"}
 :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}
 :source-paths #{"source"}
 :dependencies #(into % '[[org.clojure/clojure "1.6.0" :scope "provided"]
                          [potemkin "0.3.11"]]))

(as->
 '[
   [adzerk/boot-cljs "0.0-2760-0"]
   [adzerk/boot-cljs-repl "0.1.8"]
   [adzerk/boot-reload "0.2.4"]
   [adzerk/boot-test "1.0.3"]
   [adzerk/bootlaces "0.1.9"]
   [deraen/boot-cljx "0.2.1"]
   [jeluard/boot-notify "0.1.1"]
   [pandeiro/boot-http "0.6.1"]
   ]
 deps
 (map #(conj % :scope "test") deps)
 (set-env! :dependencies #(into % deps)))

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload :refer [reload]]
 '[adzerk.boot-test :refer [test]]
 '[adzerk.bootlaces :as bootlaces :refer [bootlaces!]]
 '[deraen.boot-cljx :refer [cljx]]
 '[jeluard.boot-notify :refer [notify]]
 '[pandeiro.boot-http :refer [serve]]
 )

(task-options! pom (get-env))

(-> :version get-env bootlaces!)

(deftask dev []
  (set-env! :source-paths #(conj % "test-source")
            :resource-paths  #(conj % "test-resource"))
  (comp
   (serve)
   (watch)
   (speak)
   #_ (notify)
   (reload :on-jsload 'com.paren.isomorphiclj-test/run-tests)
   (cljx)
   (test :namespaces '#{com.paren.isomorphiclj-test})
   (cljs-repl)
   (cljs :optimizations :none
         :source-map true
         :compiler-options {:cache-analysis true})))

(deftask build-jar []
  (comp (cljx) (bootlaces/build-jar)))

(deftask deploy-snapshot []
  (comp (build-jar) (bootlaces/push-snapshot)))

(deftask deploy-release []
  (comp (build-jar) (bootlaces/push-release)))
