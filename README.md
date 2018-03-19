# MysteryBox_CS304
Final Project for CS304

![alt text](https://raw.githubusercontent.com/hdengg/MysteryBox_CS304/blob/master/MysteryBoxRevised.jpg)

Platform:
We will use the CS UGrad Oracle installation and JDBC

Functionality:
Classes of users: customers, employees, and administrators 

Customers: 
Customers can create an account and will able to view their own account information: username, password, first and last name, phone#, email addresses, credit cards, order history and shipment history 
Customers can request to reset their password or change their first name, last name, phone number, and email 
Customers can add/delete credit cards and addresses 
They can also browse boxes by category and add or cancel a subscription to a box 

Employees: 
Can do anything a customer can do 
Ask for all account information for a given customer, where the employee enters the customer username 
Browse boxes given an mbid and their item contents; they can also search for specific items by an item id 
Can view transaction information and shipment information for all customers 
Can generate reports such as all customers subscribed from a certain date or in between certain dates 
How many customers subscribed to certain boxes, how many cancellations 

Administrators: 
Can do everything a customer and an employee can (see above) 
Can add/delete/update mystery boxes (including content and prices) 
Can add/delete/update mystery box items to the database
Update subscription fields, such as cost 
Update shipment fields such as carrier, status
