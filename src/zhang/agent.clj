(ns zhang.agent
  "Zhang agent."
  (:require [clojang.agent :as agent]
            [clojang.agent.const :as const]
            [clojang.agent.startup :as startup]
            [clojure.tools.logging :as log]
            [clojusc.twig :as logger]
            [dire.core :refer [with-handler!]]
            [trifl.net :as net]
            [zhang.agent.processes :as processes])
  (:import [java.lang.instrument])
  (:gen-class
    :methods [^:static [premain [String java.lang.instrument.Instrumentation] void]]))

(def process-table (atom nil))

(defn -premain
  [args instrument]
  (logger/set-level! '[clojang zhang] :info)
  (reset! process-table (processes/create-process-table))
  (startup/perform-node-tasks (agent/get-node-name))
  (if-not (agent/headless?)
    (startup/perform-gui-tasks)))
