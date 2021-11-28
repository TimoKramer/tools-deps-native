(ns test
  (:require [babashka.fs :as fs]
            [babashka.tasks :refer [shell]]
            [graalvm :refer [windows?]]))

(if (fs/exists? "tools.bbuild")
  (do (shell {:dir "tools.bbuild"} "git checkout babashka")
      (shell {:dir "tools.bbuild"} "git pull"))
  (do (shell "git clone https://github.com/babashka/tools.bbuild")
      (shell {:dir "tools.bbuild"} "git checkout babashka")))

(def native-executable 
  (if windows? 
    "tools-deps-native.exe"
    "tools-deps-native"))

(fs/copy native-executable "tools.bbuild" {:replace-existing true})

(shell {:dir "tools.bbuild"} "bb test")

nil
