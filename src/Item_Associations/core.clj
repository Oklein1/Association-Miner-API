(ns Item_Associations.core
  (:gen-class))

; GOAL:
;  Below is a table representing eight transactions and five items.
 ; The items are represented by their first letters, e.g., M =milk. 
 ; An X represents membership of the item in the transaction. 
 ; Find all of the rules of the form X->Y, 
 ; where X and Y are single items (not sets of two or more items), 
 ; that have confidence exactly ½. 

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


(defn find-subset [start-item]
  (let [sub-set (filter #(clojure.set/subset? start-item %) transactions)]
    (if (>= (count sub-set) 3) 
      sub-set nil)))

(defn inference [start-item infer-item]
  (filter #(clojure.set/subset? infer-item %) (remove nil? (find-subset start-item))))


(defn confidence [item infer-item]
  (let [infer-count (count (inference item infer-item))
        set-count (count (find-subset item))]
    [item infer-item (float (/ infer-count set-count))]))


(defn confidence-infer [a lat]
  (let [item-set (filter #(not (clojure.set/subset? a %)) lat)]
    (map #(confidence a %) item-set)))

(def universal-set (map #(confidence-infer % itemsets) itemsets))

(def answer (remove empty? (map (fn [u-set]
                   (filter #(= 0.5 (last %)) u-set)) (remove empty? universal-set))))
