# Mining Frequency ItemSets

## GOAL:
Imagine you are a supermarket looking to unearth consumer patterns. The data you own comes from the receits given to customers after they pay. Below is a table five items you may find on any one receit and 8 examples of transactions. The items are represented by their first letters, e.g., M =milk.  The transactions represent items found on any given receit. For example, The transaction {"b" "p"} means that a consumer only purchased beer and pepsi. For the excercise, we will concern ourselves with the quantity of any given item. 

The task at hand is to find all of the assocation pairs in the form of X->Y. To give an example, suppose X is beer and Y is juice. The task would be to identify if there is in fact a relationship between consumers who purchase beer and therefore also purchase juice. A rather famous example of Assocation pairs is {Milk, diapers} -> Beers. This is to say that consumers who purchase milk and diapers are more likely to also purchase beers. The confidence metric, i.e. the measure of how often each item in Y appears in transactions that contain items in X also, will be exactly 1/2. 




Items:
- b = beer
- c = coke
- p = pepsi
- m = milk
- j = juice

Transactions:
1. {"b" "p"}
2. {"c" "m"}
3. {"b" "c" "j"}
4. {"p" "m"}
5. {"b" "c" "m"}
6. {"m" "j"}
7. {"j"}
8. {"b" "c" "m" "j"}))
