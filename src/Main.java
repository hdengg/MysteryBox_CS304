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

        MainFrameController mfc = new MainFrameController(session, customerService, addressService, credCardService);
    }
}
