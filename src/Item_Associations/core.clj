(ns Item_Associations.core
  (:gen-class)
  (:require [Item-Associations.association-rules :refer :all]))


;; ; Legend:
;; ; b = beer
;; ; c = coke
;; ; p = pepsi
;; ; m = milk
;; ; j = juice

(def transactions '(#{"b" "p"}
                    #{"c" "m"}
                    #{"b" "c" "j"}
                    #{"p" "m"}
                    #{"b" "c" "m"}
                    #{"m" "j"}
                    #{"j"}
                    #{"b" "c" "m" "j"}))
(def itemsets #{#{"b"}
                #{"c"}
                #{"p"}
                #{"m"}
                #{"j"}})

(defn main [_]
  (find-assocation-rules 0.5 transactions itemsets))