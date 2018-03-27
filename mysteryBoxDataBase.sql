DROP TABLE Contains;
DROP TABLE Item;
DROP TABLE Shipment;
DROP TABLE Subscribes_Mystery_Box;
DROP TABLE Subscription;
DROP TABLE Mystery_Box;
DROP TABLE Pays_With;
DROP TABLE Credit_Card;
DROP TABLE Customer_Has_Address;
DROP TABLE City_Province;
DROP TABLE Address;
DROP TABLE Customer;

CREATE TABLE Customer
	(username varchar(20),
	password varchar(20),
	first_name varchar(20),
	last_name varchar(20),
	phone char(12),
	email varchar(60),
	PRIMARY KEY (username),
	UNIQUE(email));
 
GRANT SELECT ON Customer to public;

INSERT INTO Customer
VALUES ('anthonyd', 'ilovesoccer89', 'Anthony', 'Davidson', '7783945923', 'anthony@gmail.com');

INSERT INTO Customer
VALUES ('tonyvaler', 'whipeout884', 'Tony', 'Valer', '7783947294', 'tvaler@gmail.com');

INSERT INTO Customer
VALUES ('mikeman', 'mikeisthebest', 'Michael', 'James', '6045392415', 'itsmikejames@yahoo.com');

INSERT INTO Customer
VALUES ('bieberfever', 'despacito1994', 'Karen', 'Piper', '6045259604', 'flowergirl94@gmail.com');

INSERT INTO Customer
VALUES ('navigator', 'mortalkombat', 'Navjit', 'Lal', '2503948384', 'navi420@hotmail.com');
 
CREATE TABLE Address
	(house_num INTEGER,
	street varchar(40),
	postal_code varchar(6),
	PRIMARY KEY (house_num, street, postal_code));
 
GRANT SELECT ON Address to public;

/* Anthony Davidson */
INSERT INTO Address
VALUES ('1272', 'East 63rd Ave', 'V5X2L7');
INSERT INTO Address
VALUES ('3914', 'Nevelle Street', 'V6X4K7');
/* Tony Valer */
INSERT INTO Address
VALUES ('1125', 'East 54th Ave', 'V5Y7F3');
/* Michael James*/
INSERT INTO Address
VALUES ('3894', '4th Ave', 'V6S9L4');
/* Karen Piper */
INSERT INTO Address
VALUES ('2901', 'Pinetree Way', 'V9R9F1');
/* Navjit Lal*/
INSERT INTO Address
VALUES ('1834', 'David Drive', 'V2T9I2');

/* Every Customer Must be from British Columbia */
CREATE TABLE City_Province
	(city		VARCHAR(20),
	province	VARCHAR(20),
	postal_code	VARCHAR(7) NOT NULL,
	PRIMARY KEY (postal_code)
	CONSTRAINT residesInBC CHECK (province = 'British Columbia'));

GRANT SELECT ON City_Province to public;

/* Anthony Davidson */
INSERT INTO City_Province
VALUES ('Vancouver', 'British Columbia', 'V5X2L7');
INSERT INTO City_Province
VALUES ('Burnaby', 'British Columbia', 'V6X4K7');
/* Tony Valer */
INSERT INTO City_Province
VALUES ('North Vancouver', 'British Columbia', 'V5Y7F3');
/* Michael James*/
INSERT INTO City_Province
VALUES ('Nanaimo', 'British Columbia', 'V6S9L4');
/* Karen Piper */
INSERT INTO City_Province
VALUES ('Coquitlam', 'British Columbia', 'V9R9F1');
/* Navjit Lal*/
INSERT INTO City_Province
VALUES ('Burnaby', 'British Columbia', 'V4W6H4');


CREATE TABLE Customer_Has_Address
	(username varchar(20) NOT NULL,
	house_num INTEGER NOT NULL,
	street varchar(40) NOT NULL,
	postal_code varchar(6) NOT NULL,
	PRIMARY KEY (username, house_num, street, postal_code),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	FOREIGN KEY (house_num, street, postal_code) REFERENCES Address(house_num, street, postal_code) ON DELETE CASCADE);

/* Each Customer must have atleast one Address */
CREATE ASSERTION customerToAddress
CHECK
(NOT EXIST ((SELECT username FROM Customer)
			EXCEPT
			(username FROM Customer_Has_Address)));

/* Each address must have atleast one customer */
CREATE ASSERTION addressToCustomer
CHECK
(NOT EXIST ((SELECT house_num, street, postal_code FROM Address)
			EXCEPT
			(house_num, street, postal_code FROM Customer_Has_Address)));

GRANT SELECT ON Customer_Has_Address to public;

/* Anthony Davidson */
INSERT INTO Customer_Has_Address
VALUES ('anthonyd', '1272', 'East 63rd Ave', 'V5X2L7');
INSERT INTO Customer_Has_Address
VALUES ('anthonyd', '3914', 'Nevelle Street', 'V6X4K7');
/* Tony Valer */
INSERT INTO Customer_Has_Address
VALUES ('tonyvaler', '1125', 'East 54th Ave', 'V5Y7F3');
/* Michael James*/
INSERT INTO Customer_Has_Address
VALUES ('mikeman', '3894', '4th Ave', 'V6S9L4');
/* Karen Piper */
INSERT INTO Customer_Has_Address
VALUES ('bieberfever', '2901', 'Pinetree Way', 'V9R9F1');
/* Navjit Lal*/
INSERT INTO Customer_Has_Address
VALUES ('navigator', '1834', 'David Drive', 'V2T9I2');
 
CREATE TABLE Credit_Card
	(c_id INTEGER,
	exp_date date,
	token varchar(20),
	type varchar(20),
	last_digits INTEGER,
	PRIMARY KEY (c_id),
	UNIQUE(token));

GRANT SELECT ON Credit_Card to public;

/* Anthony Davidson */
INSERT INTO Credit_Card
VALUES(1001, '2020-01-01', 'XWSDFR', 'Visa', '0462');
/* Tony Valer */
INSERT INTO Credit_Card
VALUES(1002, '2024-02-01', 'XWSDFZ', 'MasterCard', '2349');
INSERT INTO Credit_Card
VALUES(2002, '2028-02-02', 'XWALFZ', 'Visa', '4926');
INSERT INTO Credit_Card
VALUES(3002, '2021-11-01', 'AQSDFZ', 'MasterCard', '2960');
/* Michael James*/
INSERT INTO Credit_Card
VALUES(1003, '2019-02-02', 'XWSDFF', 'MasterCard', '9393');
/* Karen Piper */
INSERT INTO Credit_Card
VALUES(1234, '2020-05-16', 'APGLEK', 'Visa', '1975');
/* Navjit Lal*/
INSERT INTO Credit_Card
VALUES(1972, '2025-12-12', 'FGOLWO', 'Visa', '7642');


CREATE TABLE Pays_With
	(c_id INTEGER,
	username varchar(20),
	PRIMARY KEY (c_id, username),
	FOREIGN KEY (c_id) REFERENCES Credit_Card(c_id) ON DELETE CASCADE,
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE);

/* Every Customer needs to have atleast one Credit Card */
CREATE ASSERTION customerToCreditCard
CHECK
(NOT EXIST ((SELECT username FROM Customer)
			EXCEPT
			(username FROM Pays_With));

/* Every Credit Card needs to have exactly one Customer */
/* NOT SURE HOW TO MAKE IT EXACTLY ONE */
/*
CREATE ASSERTION creditCardToCustomer
CHECK
((SELECT Count(*))); 

GRANT SELECT ON Pays_With to public;
*/

/* Anthony Davidson */
INSERT INTO Pays_With
VALUES (1001, 'anthonyd');
/* Tony Valer */
INSERT INTO Pays_With
VALUES (2002, 'tonyvaler');
/* Michael James*/
INSERT INTO Pays_With
VALUES (1003, 'mikeman');
/* Karen Piper */
INSERT INTO Pays_With
VALUES (1234, 'bieberfever');
/* Navjit Lal*/
INSERT INTO Pays_With
VALUES (1972, 'navigator');
	
CREATE TABLE Mystery_Box
	(mbid INTEGER,
	no_items INTEGER,
	mdate date,
	theme varchar(20),
	PRIMARY KEY (mbid));
 
GRANT SELECT ON Mystery_Box to public;

INSERT INTO Mystery_Box
VALUES (1, 3, '2017-12-01', 'Harry Potter');
INSERT INTO Mystery_Box
VALUES (4, 2, '2018-01-01', 'Harry Potter');
INSERT INTO Mystery_Box
VALUES (5, 3, '2018-02-01', 'Harry Potter');
INSERT INTO Mystery_Box
VALUES (2, 3, '2017-12-01', 'Anime');
INSERT INTO Mystery_Box
VALUES (6, 3, '2018-01-01', 'Anime');
INSERT INTO Mystery_Box
VALUES (7, 4, '2018-02-01', 'Anime');
INSERT INTO Mystery_Box
VALUES (3, 3, '2017-12-01','Marvel');
INSERT INTO Mystery_Box
VALUES (9, 3, '2018-01-01','Marvel');
INSERT INTO Mystery_Box
VALUES (8, 2, '2018-02-01','Marvel');


CREATE TABLE Subscription
	(s_id INTEGER,
	cost float(2),
	status varchar(10),
	s_from date,
	num_month INTEGER,
	username varchar(20) NOT NULL,
	/*mbid INTEGER NOT NULL,*/
	PRIMARY KEY (s_id),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	/*FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE*/);
 
GRANT SELECT ON Subscription to public;

/* Anthony Davidson */
INSERT INTO Subscription
VALUES (1, 20.00, 'true', '2017-11-07', 3, 'anthonyd' /*5*/);
/* Tony Valer */
INSERT INTO Subscription
VALUES (2, 50.00, 'true', '2017-12-20', 3, 'tonyvaler' /*7*/);
/* Michael James*/
INSERT INTO Subscription
VALUES (3, 25.00, 'true', '2018-01-04', 2, 'mikeman' /*9*/);
/* Karen Piper */
INSERT INTO Subscription
VALUES (4, 25.00, 'true', '2018-12-16', 2, 'bieberfever' /*9*/);
INSERT INTO Subscription
VALUES (5, 20.00, 'true', '2018-01-29', 1, 'bieberfever' /*5*/);
/* Navjit Lal*/
INSERT INTO Subscription
VALUES (6, 35.00, 'false', '2017-08-04', 1, 'navigator' /*5*/);

CREATE TABLE Subscribes_Mystery_Box
	(s_id INTEGER NOT NULL,
	mbid INTEGER NOT NULL,
	PRIMARY KEY (s_id, mbid),
	FOREIGN KEY (s_id) REFERENCES Subscription(s_id) ON DELETE CASCADE,
	FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE);

/* Every Subscription needs to have atlease one Mystery Box */
CREATE ASSERTION SubscribedToMysteryBox
CHECK
(NOT EXIST ((SELECT s_id FROM Subscription)
			EXCEPT
			(s_id FROM Subscribes_Mystery_Box)));
 
GRANT SELECT ON Subscribes_Mystery_Box to public;

CREATE TABLE Shipment
	(shipping_no INTEGER,
	carrier varchar(20),
	status varchar(20),
	ship_date date,
	tracking_no char(20),
	s_id INTEGER NOT NULL,
	PRIMARY KEY (shipping_no),
	UNIQUE(tracking_no),
	FOREIGN KEY (s_id) REFERENCES Subscription(s_id) ON DELETE CASCADE);
 
GRANT SELECT ON Shipment to public;

/* Anthony Davidson */
INSERT INTO Shipment
VALUES (1468, 'Fedex', 'Arrived', '2017-12-05', 'W1Z98', 1);
INSERT INTO Shipment
VALUES (1975, 'Fedex', 'Arrived', '2018-01-12', 'A01FK', 1);
INSERT INTO Shipment
VALUES (3594, 'Fedex', 'Arrived', '2018-02-03', 'O91KG', 1);
/* Tony Valer */
INSERT INTO Shipment
VALUES (2128, 'Canada Post', 'Arrived', '2017-12-05', 'A93LG', 2);
INSERT INTO Shipment
VALUES (1945, 'Canada Post', 'Arrived', '2018-01-09', 'GK29G', 2);
INSERT INTO Shipment
VALUES (182, 'Canada Post', 'Pending', NULL, NULL, 2);
/* Michael James*/
INSERT INTO Shipment
VALUES (372, 'UPS', 'Arrived', '2018-01-04', 'O9KF8', 3);
INSERT INTO Shipment
VALUES (7352, 'UPS', 'Pending', NULL, NULL, 3);
/* Karen Piper */
INSERT INTO Shipment
VALUES (195, 'Fedex', 'Arrived', '2018-01-10', 'KG89', 4);
INSERT INTO Shipment
VALUES (1297, 'Fedex', 'Arrived', '2018-02-07', '0G1KL', 4);
INSERT INTO Shipment
VALUES (8426, 'Fedex', 'Pending', NULL, NULL, 5);
/* Navjit Lal*/
INSERT INTO Shipment
VALUES (168, 'UPS', 'Arrived', '2017-12-25', 'UK911', 6);
 
CREATE TABLE Item
	(item_id INTEGER,
	value float(2),
	item_name varchar(40),
	PRIMARY KEY (item_id));
 
GRANT SELECT ON Item to public;

/* Harry Potter */
INSERT INTO Item VALUES (1, 5.00, 'Harry Potter Plush Toy');
INSERT INTO Item VALUES (2, 4.00, 'Pheonix Feather');
INSERT INTO Item VALUES (3, 10.00, 'Hermione Wand');
INSERT INTO Item VALUES (4, 5.00, 'Hermione Granger Plush Toy');
INSERT INTO Item VALUES (5, 10.00, 'Snitch');
INSERT INTO Item VALUES (6, 5.00, 'Ron Weasley Plush Toy');
INSERT INTO Item VALUES (7, 15.00, 'Gryffindor scarf');
INSERT INTO Item VALUES (8, 5.00, 'Draco Malfoy Plush Toy');
/* Anime */
INSERT INTO Item VALUES (9, 5.00, 'Naruto Plush Toy');
INSERT INTO Item VALUES (10, 8.00, 'Shuriken');
INSERT INTO Item VALUES (11, 6.00, 'Ninja Headband');
INSERT INTO Item VALUES (12, 15.00, 'Fullmetal Alchemist Figurine');
INSERT INTO Item VALUES (13, 5.00, 'SAO Mini Sword');
INSERT INTO Item VALUES (14, 7.00, 'Magika Keychain');
INSERT INTO Item VALUES (15, 5.00, 'One Punch Man Plush Toy');
INSERT INTO Item VALUES (16, 7.00, 'Mini Excalibur');
INSERT INTO Item VALUES (17, 9.00, 'Saber Figurine');
INSERT INTO Item VALUES (18, 12.00, 'Psychopass Gun');
/* Marvel */
INSERT INTO Item VALUES (19, 5.00, 'Ironman Plush Toy');
INSERT INTO Item VALUES (20, 4.00, 'Hulk Plush Toy');
INSERT INTO Item VALUES (21, 5.00, 'Hawkeye Plush Toy');
INSERT INTO Item VALUES (22, 12.00, 'Black Panther Figurine');
INSERT INTO Item VALUES (23, 10.00, 'Bow and Arrows');
INSERT INTO Item VALUES (24, 6.00, 'Ironman Mask');
INSERT INTO Item VALUES (25, 10.00, 'Thor Hammer');
INSERT INTO Item VALUES (26, 8.00, 'Captain America Shield');


CREATE TABLE Contains
	(mbid INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	PRIMARY KEY (mbid, item_id),
	FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE,
	FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE);

/* Every MysteryBox must contain atleast one item */
CREATE ASSERTION mysteryBoxContainsItem
CHECK
(NOT EXIST ((SELECT mbid FROM MysteryBox)
			EXCEPT
			(mbid FROM Contains)));

/* Every Item must be in atleast one MysteryBox */
CREATE ASSERTION itemInMysteryBox
CHECK
(NOT EXIST ((SELECT item_id FROM Item)
			EXCEPT
			(item_id FROM Contains)));
 
GRANT SELECT ON Contains to public;

/* Harry Potter */
INSERT INTO Contains VALUES (1, 1);
INSERT INTO Contains VALUES (1, 2);
INSERT INTO Contains VALUES (1, 3);
INSERT INTO Contains VALUES (4, 4);
INSERT INTO Contains VALUES (4, 5);
INSERT INTO Contains VALUES (5, 6);
INSERT INTO Contains VALUES (5, 7);
INSERT INTO Contains VALUES (5, 8);
/* Anime */
INSERT INTO Contains VALUES (2, 9);
INSERT INTO Contains VALUES (2, 10);
INSERT INTO Contains VALUES (2, 11);
INSERT INTO Contains VALUES (6, 12);
INSERT INTO Contains VALUES (6, 13);
INSERT INTO Contains VALUES (6, 14);
INSERT INTO Contains VALUES (7, 15);
INSERT INTO Contains VALUES (7, 16);
INSERT INTO Contains VALUES (7, 17);
INSERT INTO Contains VALUES (7, 18);
/* Marvel */
INSERT INTO Contains VALUES (3, 19);
INSERT INTO Contains VALUES (3, 20);
INSERT INTO Contains VALUES (3, 21);
INSERT INTO Contains VALUES (8, 22);
INSERT INTO Contains VALUES (8, 23);
INSERT INTO Contains VALUES (8, 24);
INSERT INTO Contains VALUES (9, 25);
INSERT INTO Contains VALUES (9, 26);
