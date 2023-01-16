(ns Item-Associations.association-rules
  (:gen-class))



(defn find-subset [start-item transactions]
  (filter #(clojure.set/subset? start-item %) transactions))

(defn inference [start-item infer-item transactions]
  (filter #(clojure.set/subset? infer-item %) (find-subset start-item transactions)))


(defn confidence-infer [start-item subset transactions]
  (defn confidence [item infer-item]
    "Calculates the confidence metric used later determine association pairs.
    - Returns a vector of 3 items: 
      [start-item, inferred-item, confidence metric]"
    (let [infer-count (count (inference item infer-item transactions))
          set-count (count (find-subset item transactions))]
      [item infer-item (float (/ infer-count set-count))]))
  (let [item-set (filter #(not (clojure.set/subset? start-item %)) subset)]
    (mapv #(confidence start-item %) item-set)))


(defn find-assocation-rules [confidence-metric transactions itemsets]
  "Derives all possible association-pairs within the universal set that are greater than or equal to half, 0.5.
   Confidence metric needs to be a float representing a decimal percentage."
  (let [universal-set (map #(confidence-infer % itemsets transactions) itemsets)]
    (remove empty? (map (fn [u-set]
                          (filter #(>= confidence-metric (last %)) u-set)) (remove empty? universal-set)))))
