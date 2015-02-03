(ns com.paren.isomorphiclj
  (:refer-clojure :exclude [identical?])
  (:require
   #+clj [potemkin]
   #+clj [clojure.core :as core]
   #+cljs [cljs.core :as core :include-macros true]))

#+clj (potemkin/import-fn core/identical?)
#+cljs (def ^:boolean identical? keyword-identical?)
