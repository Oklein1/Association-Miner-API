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
  (filter #(clojure.set/subset? start-item %) transactions))

(defn inference [start-item infer-item]
  (filter #(clojure.set/subset? infer-item %) (find-subset start-item)))


(defn confidence [item infer-item]
  (let [infer-count (count (inference item infer-item))
        set-count (count (find-subset item))]
    [item infer-item (float (/ infer-count set-count))]))


(defn confidence-infer [a lat]
  (let [item-set (filter #(not (clojure.set/subset? a %)) lat)]
    (map #(confidence a %) item-set)))

(def universal-set (map #(confidence-infer % itemsets) itemsets))

(def answer (map (fn [u-set]
                   (filter #(>= 0.5 (last %)) u-set)) universal-set))




(defn find-dups? [a lat]
  (filter (fn [lst]
            (let [[lst-f lst-s lst-n] lst
                  [a-f a-s a-n] a]
              (and (and (= lst-f lst-s) (= a-s a-f))
                   (= lst-n a-n))))) lat)


(map (fn [u-set]
       (filter #(find-dups? % universal-set) u-set)) universal-set)






