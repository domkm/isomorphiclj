(ns com.paren.isomorphiclj-test
  (:require
   [com.paren.isomorphiclj :as iso]
   #+clj [clojure.test :as t]
   #+cljs [cljs.test :as t :include-macros true]))

(t/deftest test-identical?
  #+clj (t/is (= identical? iso/identical?))
  #+cljs (t/are [x y] (iso/identical? x y)
                :k :k
                0 0
                true true))

;;;

#+cljs
(defn run-tests []
  (enable-console-print!)
  (t/run-tests))
