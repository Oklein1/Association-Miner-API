(ns Item_Associations.core
  (:gen-class))

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

(def answer (map (fn [u-set]
                   (filter #(= 0.5 (last %)) u-set)) (remove empty? universal-set)))




