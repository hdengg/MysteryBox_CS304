import service.*;
import client.MainFrameController;
import model.Session;

public class Main {

    public static void main(String[] args) {
        ConnectionService connectionService = new ConnectionService();
        connectionService.connect("ora_c1z8", "a41584103");
        Session session = new Session();
        CustomerService customerService = new CustomerService(connectionService.getConnection());
        MysteryBoxService mysteryBoxService = new MysteryBoxService(connectionService.getConnection());
        ItemService itemService = new ItemService(connectionService.getConnection());
        ContainsService containsService = new ContainsService(connectionService.getConnection());
        MainFrameController mfc = new MainFrameController(customerService);
    }
}