DROP TABLE Contains;
DROP TABLE Item;
DROP TABLE Shipment;
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
	PRIMARY KEY (username));
 
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
/* Mike */
INSERT INTO Address
VALUES ('3894', '4th Ave', 'V6S9L4');

CREATE TABLE City_Province
	(city		VARCHAR(20),
	province	VARCHAR(20),
	postal_code	VARCHAR(7) NOT NULL,
	PRIMARY KEY (postal_code),
	FOREIGN KEY (postal_code) REFERENCES Address(postal_code) ON DELETE CASCADE);

GRANT SELECT ON City_Province to public;

/* Anthony Davidson */
INSERT INTO City_Province
VALUES ('Vancouver', 'British Columbia', 'V5X2L7');
INSERT INTO City_Province
VALUES ('Burnaby', 'British Columbia', 'V6X4K7');
/* Tony Valer */
INSERT INTO City_Province
VALUES ('North Vancouver', 'British Columbia', 'V5Y7F3');
/* Mike */
INSERT INTO City_Province
VALUES ('Nanaimo', 'British Columbia', 'V6S9L4');


CREATE TABLE Customer_Has_Address
	(username varchar(20) NOT NULL,
	house_num varchar(20) NOT NULL,
	street varchar(40) NOT NULL,
	postal_code varchar(6) NOT NULL,
	PRIMARY KEY (username, house_num, street, postal_code),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	FOREIGN KEY (house_num, street, postal_code) REFERENCES Address(house_num, street, postal_code) ON DELETE CASCADE);

GRANT SELECT ON Customer_Has_Address to public;

/* Anthony Davidson */
INSERT INTO Customer_Has_Address
VALUES ('anthonyd', '1272', 'East 63rd Ave', 'V5X2L7');
INSERT INTO Customer_Has_Address
VALUES ('anthonyd', '3914', 'Nevelle Street', 'V6X4K7');
/* Tony Valer */
INSERT INTO Customer_Has_Address
VALUES ('tonyvaler', '1125', 'East 54th Ave', 'V5Y7F3');
/* Mike */
INSERT INTO Customer_Has_Address
VALUES ('mikeman', '3894', '4th Ave', 'V6S9L4');
 
CREATE TABLE Credit_Card
	(c_id INTEGER,
	exp_date date,
	token varchar(20),
	type varchar(20),
	last_digits INTEGER,
	PRIMARY KEY (c_id));

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
/* Mike */
INSERT INTO Credit_Card
VALUES(1003, '2019-02-02', 'XWSDFF', 'MasterCard', '9393');


CREATE TABLE Pays_With
	(c_id INTEGER,
	username varchar(20),
	PRIMARY KEY (c_id, username),
	FOREIGN KEY (c_id) REFERENCES Credit_Card(c_id) ON DELETE CASCADE,
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE);

GRANT SELECT ON Pays_With to public;

/* Anthony Davidson */
INSERT INTO Pays_With
VALUES (1001, 'anthonyd');
/* Tony Valer */
INSERT INTO Pays_With
VALUES (2002, 'tonyvaler');
/* Mike */
INSERT INTO Pays_With
VALUES (1003, 'mikeman');


	
CREATE TABLE Mystery_Box
	(mbid INTEGER,
	no_items INTEGER,
	mdate date,
	theme varchar(20),
	PRIMARY KEY (mbid));
 
GRANT SELECT ON Mystery_Box to public;

/* Anthony Davidson */
INSERT INTO Mystery_Box
VALUES (1, 3, '2017-12-27', 'Harry Potter');
/* Tony Valer */
INSERT INTO Mystery_Box
VALUES (2, 3, '2017-12-27', 'Anime');
/* Mike */
INSERT INTO Mystery_Box
VALUES (3, 3, '2017-09-01','Marvel');

CREATE TABLE Subscription
	(s_id INTEGER,
	cost float(2),
	status varchar(10),
	s_from date,
	num_month INTEGER,
	username varchar(20) NOT NULL,
	mbid INTEGER NOT NULL,
	PRIMARY KEY (s_id),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE);
 
GRANT SELECT ON Subscription to public;

/* Anthony Davidson */
INSERT INTO Subscription
VALUES (1, 20.00, 'true', '2017-12-07', 4, 'anthonyd', 1);
/* Tony Valer */
INSERT INTO Subscription
VALUES (2, 10.00, 'true', '2017-11-02', 2, 'tonyvaler', 2);
/* Mike */
INSERT INTO Subscription
VALUES (3, 20.00, 'true', '2017-08-04', 4, 'mikeman', 3);
 
CREATE TABLE Shipment
	(shipping_no INTEGER,
	carrier varchar(20),
	ship_date date,
	status varchar(20),
	tracking_no char(20),
	s_id INTEGER NOT NULL,
	PRIMARY KEY (shipping_no),
	FOREIGN KEY (s_id) REFERENCES Subscription(s_id) ON DELETE CASCADE);
 
GRANT SELECT ON Shipment to public;

/* Anthony Davidson */
INSERT INTO Shipment
VALUES (1, 'Fedex', '2018-01-12', 'Arrived', 'W1Z98', 1);
/* Tony Valer */
INSERT INTO Shipment
VALUES (2, 'Canada Post', NULL, 'Pending', NULL, 2);
/* Mike */
INSERT INTO Shipment
VALUES (3, 'UPS', NULL, 'Pending', NULL, 3);
 
CREATE TABLE Item
	(item_id INTEGER,
	value float(2),
	item_name varchar(40),
	PRIMARY KEY (item_id));
 
GRANT SELECT ON Item to public;

/* Anthony Davidson */
INSERT INTO Item
VALUES (1, 5.00, 'Harry Potter Plush Toy');
/* Tony Valer */
INSERT INTO Item
VALUES (2, 5.00, 'Naruto Plush Toy');
/* Mike */
INSERT INTO Item
VALUES (3, 5.00, 'Avengers Toy Set');


CREATE TABLE Contains
	(mbid INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	PRIMARY KEY (mbid, item_id),
	FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE,
	FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE);
 
GRANT SELECT ON Contains to public;

/* Anthony Davidson */
INSERT INTO Contains
VALUES (1, 1);
/* Tony Valer */
INSERT INTO Contains
VALUES (2, 2);
/* Mike */
INSERT INTO Contains
VALUES (3, 3);


