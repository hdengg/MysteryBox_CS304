import client.MainFrameController;
import model.Session;
import service.*;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }

        ConnectionService connectionService = ConnectionService.getInstance();

        //connectionService.connect("ora_w8t0b", "a86182169");
        connectionService.connect(host, "ora_c1z8", "a41584103");
        Connection connection = connectionService.getConnection();
        Session session = Session.getInstance();
        CustomerService customerService = new CustomerService(connection);
        MysteryBoxService mysteryBoxService = new MysteryBoxService(connection);
        ItemService itemService = new ItemService(connection);
        AddressService addressService = new AddressService(connection);
        CreditCardService credCardService = new CreditCardService(connection);

        //janiceTester(customerService, addressService, credCardService, shipmentService, subscriptionService, session);

        MainFrameController mfc = new MainFrameController(session, customerService, addressService, credCardService);
    }

    public static void janiceTester(CustomerService customerService, AddressService addressService,
                                    CreditCardService credCardService, ShipmentService shipmentService,
                                    SubscriptionService subscriptionService, Session session) {
//        customerService.login(session, "mikeman", "mikeisthebest");
//        System.out.println(session.getCustomer().toString());
//
//        Customer customer = session.getCustomer();
//
//        customer = customerService.updateCustomer("mikeman", "newp", customer.getFirstName(),
//                customer.getLastName(), customer.getPhoneNum(), customer.getEmail());
//
//        System.out.println(customer.toString());
//
//        ArrayList<AddressUI> mikeAddresses = addressService.getAllCustomerAddresses("anthonyd");
//
//        for (AddressUI a : mikeAddresses) {
//            System.out.println(a.toString());
//        }
//
//        addressService.addAddress("mikeman", 11, "STREEET", "PCODE", "CITY", "PROV");
//        addressService.deleteAddress("mikeman", 11, "STREEET", "PCODE");
//
//        AddressUI mikeAddress = new AddressUI(3894, "4th Ave", "V6S9L4", "Nanaimo", "British Columbia");
//        addressService.updateAddress("mikeman", mikeAddress, 99, "newStreet", "newPC", "newCity", "newProv");
//
//        ArrayList<CreditCard> mikecards = credCardService.getCustomerCreditCards("mikeman");
//        for (CreditCard cc : mikecards) {
//            System.out.println(cc.toString());
//        }
//
//        Date date = new Date(1522027111);
//        CreditCard mikeCard = new CreditCard(919, date, "KEN", "TYPO", 3333);
//        credCardService.addCustomerCreditCard("mikeman", mikeCard);
//        credCardService.deleteCustomerCreditCard("mikeman", 919);

//        ArrayList<Customer> customers = customerService.getAllCustomers();
//        for (Customer c : customers) {
//            System.out.println(c.toString());
//        }

//        try {
//            Date date = new Date(1522027111);
//            Shipment shipment = new Shipment(999, "carrier", "zombie", date, "W8W8W");
//            shipmentService.deleteShipment(999);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            Address a = new Address(3894, "4th Ave", "V6S9L4", "Nanaimo", "British Columbia");
//
//            addressService.updateAddress("mikeman", a, 4949, "4th Ave", "V6S9L4",
//                    "Nanaimo", "British Columbia");
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            int housenum = 77;
//            String street = "asdfasdf";
//            String postalCode = "888888";
//            addressService.insertAddress(housenum, street, postalCode);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            String city = "WASSAUP";
//            String province = "British Columbia";
//            String postalCode = "YOYOYO";
//            addressService.insertCityProvince(city, province, postalCode);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        Date date = new Date(1522027111);
//        CreditCard cc = new CreditCard(99, date, "TOKEN", "type", 999);
//
//        try {
//            credCardService.addCustomerCreditCard("mikeman", cc);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
