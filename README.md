# MysteryBox_CS304
Final Project for CS304

![ER Diagram](https://github.com/hdengg/MysteryBox_CS304/blob/master/MysteryBoxRevised.jpg)

### DEMO DAY CHECKLIST 
- [x] **2 MARKS** Selection Query & Projection Query </br >
Filter Shipments by Carrier. Can use projection fields. 
- [x] **1 MARK** Join Query 
Gets the credit cards from user 
```
SELECT Credit_Card.* FROM Pays_With INNER JOIN Credit_Card ON Credit_Card.c_id = Pays_With.c_id WHERE username = ?;
```
- [x] **2 MARKS** Division Query </br >
Once we run this query, insert themed boxes into the Subscribes_To table so the user is subscribed to boxes of all themes. Run query again and this user will be added to the list. 
```
SELECT username FROM Subscription S WHERE NOT EXISTS (SELECT M.theme FROM Mystery_Box M WHERE NOT EXISTS (SELECT * FROM Subscription NATURAL JOIN Subscribes_To NATURAL JOIN Mystery_Box as J WHERE J.theme = M.theme AND J.s_id = S.s_id));
```
- [x] **2 MARKS** Aggregation Query </br >
Query #1: Gets the number of subscriptions to a theme
```
SELECT mb.theme, COUNT(st.s_id) as total FROM Subscribes_To st NATURAL INNER JOIN Mystery_Box mb WHERE mb.theme = ? GROUP BY mb.theme);
```
Query #2: Gets the total cost of all subscribed boxes
```
SELECT SUM(cost) as total FROM Subscribes_To NATURAL JOIN Mystery_Box
```
- [x] **4 MARKS** Nested Aggregation Query
Query #1: Gets the box and max/min average value over all items 
```
WITH inner_table AS (SELECT temp.mbid, temp.avgprice FROM (SELECT Mystery_Box.mbid, AVG(Item.value) AS avgprice FROM Mystery_Box LEFT JOIN Contains ON Mystery_Box.mbid = Contains.mbid LEFT JOIN Item ON Item.item_id = Contains.item_id GROUP BY Mystery_Box.mbid) temp) SELECT * FROM inner_table WHERE avgprice = (SELECT [MAX/MIN](avgprice) FROM inner_table);
```
Query #2: Gets the theme with the most subscribers over all themes 
``` 
WITH temp_table AS (SELECT mb.theme, COUNT(st.s_id) as sub_counts FROM Subscribes_To st NATURAL JOIN Mystery_Box mb GROUP BY mb.theme) temp) SELECT * FROM temp_table WHERE sub_counts = (SELECT MAX(sub_counts) FROM temp_table);
```
- [x] **4 MARKS** Deletions: </br >
Case 1: Delete an item. Now get all items from a box that had that item in it. The box no longer contains that item because of the cascading effect </br >
Case 2: Janice has one

- [x] **2 MARKS** Update Constraint: </br >
Updating an item with a negative value. 

- [x] **3 MARKS** GUI:  
- Type checks 
- Error handling 
- Functional interface 

### HOW TO RUN ON UGRAD SERVER:
- scp the project to the ugrad server and ssh -X to the ugrad server.
- cd into this: cd ~/304Project/MysteryBox_CS304/production/304Project
- then run java Main 

### HOW TO RUN ON INTELLIJ:
https://docs.google.com/document/d/1zcNB4Z_VKEWk7deZixsrO84K4aP0jyz7MufFAh2Ov3k/edit?usp=sharing

### QUERY CHECKLIST:
https://docs.google.com/document/d/1MVYEyPK213wZI0meHWWs1zrOsyhGZVE2lMrkrqxTVzo/edit
