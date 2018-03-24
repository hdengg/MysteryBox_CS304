DROP TABLE Mystery_Box_Item;
DROP TABLE Item;
DROP TABLE Shipment_Process;
DROP TABLE Subscription;
DROP TABLE Mystery_Box;
DROP TABLE Customer_Payment;
DROP TABLE Credit_Card;
DROP TABLE Customer_Address;
DROP TABLE Address;
DROP TABLE Customer;


CREATE TABLE Customer
	(username varchar(20) ,
	password varchar(20) ,
	first_name varchar(20) ,
	last_name varchar(20) ,
	phone char(12) ,
	email varchar(60) ,
	PRIMARY KEY (username));
 
GRANT SELECT ON Customer to public;
 
CREATE TABLE Address
	(house_num varchar(20) ,
	street varchar(40) ,
	postal_code varchar(6),
	city	varchar(20),
	province varchar(20),
	PRIMARY KEY (house_num, street, postal_code));
 
GRANT SELECT ON Address to public;


CREATE TABLE Customer_Address
	(username varchar(20) NOT NULL,
	house_num varchar(20) NOT NULL,
	street varchar(40) NOT NULL,
	postal_code varchar(6) NOT NULL,
	PRIMARY KEY (username, house_num, street, postal_code),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	FOREIGN KEY (house_num, street, postal_code) REFERENCES Address(house_num, street, postal_code) ON DELETE CASCADE);

GRANT SELECT ON Customer_Address to public;
 
CREATE TABLE Credit_Card
	(c_id INTEGER,
	exp_date date,
	token varchar(20),
	type varchar(20),
	last_digits INTEGER,
	PRIMARY KEY (c_id));

GRANT SELECT ON Credit_Card to public;

CREATE TABLE Customer_Payment
	(username varchar(20),
	c_id INTEGER,
	PRIMARY KEY (username, c_id),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	FOREIGN KEY (c_id) REFERENCES Credit_Card(c_id) ON DELETE CASCADE);


GRANT SELECT ON Customer_Payment to public;
	
CREATE TABLE Mystery_Box
	(mbid INTEGER,
	num_items INTEGER,
	theme varchar(20),
	mdate date,
	PRIMARY KEY (mbid));
 
GRANT SELECT ON Mystery_Box to public;

CREATE TABLE Subscription
	(s_id INTEGER,
	cost float(2),
	status varchar(10),
	s_from date,
	momth_num INTEGER,
	username varchar(20) NOT NULL,
	mbid INTEGER NOT NULL,
	PRIMARY KEY (s_id),
	FOREIGN KEY (username) REFERENCES Customer(username) ON DELETE CASCADE,
	FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE);
 
GRANT SELECT ON SubscriptiON to public;

 
CREATE TABLE Shipment_Process
	(shipping_no INTEGER,
	carrier varchar(20),
	ship_date date,
	status varchar(20),
	tracking_no char(20),
	s_id INTEGER NOT NULL,
	PRIMARY KEY (shipping_no),
	FOREIGN KEY (s_id) REFERENCES Subscription(s_id) ON DELETE CASCADE);
 
GRANT SELECT ON Shipment_Process to public;
 
CREATE TABLE Item
	(item_id INTEGER,
	value float(2),
	item_name varchar(40),
	PRIMARY KEY (item_id));
 
GRANT SELECT ON Item to public;

CREATE TABLE Mystery_Box_Item
	(item_id INTEGER NOT NULL,
	mbid INTEGER NOT NULL,
	PRIMARY KEY (item_id, mbid),
	FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE,
	FOREIGN KEY (mbid) REFERENCES Mystery_Box(mbid) ON DELETE CASCADE);
 
GRANT SELECT ON Mystery_Box_Item to public;



INSERT INTO Credit_Card
VALUES(1001, '2020-01-01', 'XWSDFR', 'Visa', '0462');
 
INSERT INTO Customer
VALUES ('anthonyd', 'ilovesoccer89', 'Anthony', 'Davidson', '7783945923', 'anthony@gmail.com');

INSERT INTO Customer_Payment
VALUES ('anthonyd', 1001);

INSERT INTO Address
VALUES ('1272', 'East 63rd Ave', 'V5X2L7', 'Vancouver', 'British Columbia');

INSERT INTO Customer_Address
VALUES ('anthonyd', '1272', 'East 63rd Ave', 'V5X2L7');

INSERT INTO Subscription
VALUES (1, 20.00, 'true', '2017-12-07', 4, 'anthonyd', 1);

INSERT INTO Mystery_Box
VALUES (1, 3, 'Harry Potter', '2017-12-27');

INSERT INTO Shipment_Process
VALUES (1, 'Fedex', '2018-01-12', 'Arrived', 'W1Z98', 1);

INSERT INTO Item
VALUES (1, 5.00, 'Harry Potter Plush Toy');

INSERT INTO Mystery_Box_Item
VALUES (1, 1);



INSERT INTO Credit_Card
VALUES(1002, '2024-02-01', 'XWSDFZ', 'MasterCard', '2349');
 
INSERT INTO Customer
VALUES ('tonyvaler', 'whipeout884', 'Tony', 'Valer', '7783947294', 'tvaler@gmail.com');

INSERT INTO Customer_Payment
VALUES ('tonyvaler', 1002);

INSERT INTO Address
VALUES ('1125', 'East 54th Ave', 'V5Y7F3', 'North Vancouver', 'British Columbia');

INSERT INTO Customer_Address
VALUES ('tonyvaler', '1125', 'East 54th Ave', 'V5Y7F3');

INSERT INTO Subscription
VALUES (2, 20.00, 'true', '2017-11-02', 2, 'tonyvaler', 2);

INSERT INTO Mystery_Box
VALUES (2, 3, 'Anime', '2017-12-27');

INSERT INTO Shipment_Process
VALUES (2, 'UPS', NULL, 'Pending', NULL, 2);

INSERT INTO Item
VALUES (2, 5.00, 'Naruto Plush Toy');

INSERT INTO Mystery_Box_Item
VALUES (2, 2);



INSERT INTO Credit_Card
VALUES(1003, '2019-02-02', 'XWSDFF', 'Visa', '9393');
 
INSERT INTO Customer
VALUES ('mikeman', 'mikeisthebest', 'Michael', 'James', '6045392415', 'itsmikejames@yahoo.com');

INSERT INTO Customer_Payment
VALUES ('mikeman', 1003);

INSERT INTO Address
VALUES ('3894', '4th Ave', 'V6S9L4', 'Nanaimo', 'British Columbia');

INSERT INTO Customer_Address
VALUES ('mikeman', '3894', '4th Ave', 'V6S9L4');

INSERT INTO Subscription
VALUES (3, 20.00, 'true', '2017-08-04', 4, 'mikeman', 3);

INSERT INTO Mystery_Box
VALUES (3, 3, 'Marvel', '2017-09-01');

INSERT INTO Shipment_Process
VALUES (3, 'UPS', NULL, 'Pending', NULL, 3);

INSERT INTO Item
VALUES (3, 5.00, 'Avengers Toy Set');

INSERT INTO Mystery_Box_Item
VALUES (3, 3);




INSERT INTO Credit_Card
VALUES(1004, '2021-09-06', 'XWSDFE', 'MasterCard', '0921');
 
INSERT INTO Customer
VALUES ('bieberfever', 'despacito1994', 'Karen', 'Piper', '6045259604', 'flowergirl94@gmail.com');

INSERT INTO Customer_Payment
VALUES ('bieberfever', 1004);

INSERT INTO Address
VALUES ('3059', 'Pandosey Rd', 'N7C3L6', 'Kelowna', 'British Columbia');

INSERT INTO Customer_Address
VALUES ('bieberfever', '3059', 'Pandosey Rd', 'N7C3L6');

INSERT INTO Subscription
VALUES (4, 20.00, 'true', '2018-01-04', 4, 'bieberfever', 4);

INSERT INTO Mystery_Box
VALUES (4, 4, 'Harry Potter', '2018-02-01');

INSERT INTO Shipment_Process
VALUES (4, 'UPS', '2018-02-14', 'Arrived', 'L1MNA', 4);

INSERT INTO Item
VALUES (4, 5.00, 'Chamber of Secrets Box');

INSERT INTO Mystery_Box_Item
VALUES (4, 4);



INSERT INTO Credit_Card
VALUES(1005, '2022-05-05', 'XWSDFO', 'MasterCard', '0581');
 
INSERT INTO Customer
VALUES ('navigator', 'mortalkombat', 'Navjit', 'Lal', '2503948384', 'navi420@hotmail.com');

INSERT INTO Customer_Payment
VALUES ('navigator', 1005);

INSERT INTO Address
VALUES ('19284', 'White Street', 'V8S3L8', 'Ladysmith', 'British Columbia');

INSERT INTO Customer_Address
VALUES ('navigator', '19284', 'White Street', 'V8S3L8');

INSERT INTO Subscription
VALUES (5, 20.00, 'true', '2018-01-04', 4, 'navigator', 5);

INSERT INTO Mystery_Box
VALUES (5, 5, 'Harry Potter', '2018-02-01');

INSERT INTO Shipment_Process
VALUES (5, 'UPS', NULL, 'Pending', NULL, 5);

INSERT INTO Item
VALUES (5, 5.00, 'Magic Wand');

INSERT INTO Mystery_Box_Item
VALUES (5, 5);


