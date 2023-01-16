**Summary of Problem:**

Below is a table representing eight transactions and five items: Beer, Coke, Pepsi, Milk, and Juice. The items are represented by their first letters, e.g., M =milk. An X represents membership of the item in the transaction. Find all of the rules of the form X->Y, where X and Y are single items (not sets of two or more items), that have confidence greater or equal to 1/2.

All rules of the for X->Y that have a confidence of exactly 1/2 are the following:
1. Juice -> Milk
2. Beer -> Milk
3. Juice -> Beer
4. Beer -> Juice
5. Coke -> Juice
6. Juice -> Coke



```clojure

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
```

Below one can find all the functions necessary to derive the answer:

```clojure
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


(defn find-assocation-rules [transactions itemsets]
  (let [universal-set (map #(confidence-infer % itemsets transactions) itemsets)]
    (remove empty? (map (fn [u-set]
                          (filter #(= 0.5 (last %)) u-set)) (remove empty? universal-set)))))

```

The <code>find-subset</code> and <code>inference</code> functions in combination merely derive subsets of subsets. For instance, if we wish to know if there is a possible association rule between, say, "coke" and "juice," then <code>find-subset</code> returns a subset of transactions where "coke" is within the set. Therefrom, the <code>inference</code> function looks for all "juice" within the subset previously constructed. Having these two functions, we can construct <code>confidence</code> function, which returns 3 items within a clojure vector: the item, the interfered item, and the confidence score. The confidence score is calculated by taking the total count of items derived by dividing the total count derived by the <code>inference</code> function over the total count derived by the <code>find-subset</code>. Below one can find an example:

```clojure
[#{"c"} #{"j"} 0.5]
```

<code>confidence</code> takes all the aforementioned functions and combines them by checking every item-set to reveal if any imply the other. So, for example, "beer" will be checked against "coke," "pepsi," "milk," and "juice." Then, coke will be checked against all the other items, and so on. Finally, we arrive at our main function <code>find-assocation-rules</code> derives all possible sets within the universal set that are exactly 0.5.